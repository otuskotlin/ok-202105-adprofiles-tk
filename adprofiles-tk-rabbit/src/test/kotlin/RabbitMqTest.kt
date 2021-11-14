import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import main.RabbitConfig
import main.RabbitController
import main.RabbitDirectProcessor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import ru.adprofiles.service.AdService
import ru.tk.adprofiles.kmp.transport.models.AdDto

internal class RabbitMqTest {

    companion object {
        const val keyIn = "key-in"
        const val keyOut = "key-Out"
        const val exchange = "test-exchange"
        const val queue = "test-queue"
        val container by lazy {
            RabbitMQContainer("rabbitmq:latest").apply {
                withUser("guest", "guest")
                start()
            }
        }

        val rabbitMqTestPort: Int by lazy {
            container.getMappedPort(5672)
        }
        val config by lazy {
            RabbitConfig(
                port = rabbitMqTestPort
            )
        }
        val service = AdService()
        val processor by lazy {
            RabbitDirectProcessor(
                config = config,
                keyIn = keyIn,
                keyOut = keyOut,
                exchange = exchange,
                queue = queue,
                service = service,
                consumerTag = "test-tag"
            )
        }
        val controller by lazy {
            RabbitController(
                processors = setOf(processor)
            )
        }
        val mapper = ObjectMapper()
    }

    @BeforeTest
    fun tearUp() {
        controller.start()
    }

    @Test
    fun adCreateTest() {
        ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.getBody(), Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, mapper.writeValueAsBytes(AdDto(id = "id",title = "create")))

                runBlocking {
                    withTimeoutOrNull(250L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = mapper.readValue(responseJson, AdDto::class.java)
                val expected = AdDto(id = "id",title = "create")
                assertEquals(expected.id, response.id)
                assertEquals(expected.title, response.title)

            }
        }
    }
}

package main

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.runBlocking
import ru.adprofiles.service.AdService
import ru.tk.adprofiles.kmp.transport.models.AdDto

class RabbitDirectProcessor(
    config: RabbitConfig,
    consumerTag: String = "",
    private val keyIn: String,
    private val keyOut: String,
    private val exchange: String,
    private val queue: String,
    private val service: AdService,
) : RabbitProcessorBase(config, consumerTag) {
    private val jacksonMapper = ObjectMapper()

    override fun Channel.getDeliveryCallback(): DeliverCallback {
        val channel = this
        return DeliverCallback { tag, message ->
            runBlocking {
                try {
                    val query = jacksonMapper.readValue(message.body, AdDto::class.java)
                    val response = service.createAd(query)
                    jacksonMapper.writeValueAsBytes(response).also {
                        channel.basicPublish(exchange, keyOut, null, it)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    val response = e.message
                    jacksonMapper.writeValueAsBytes(response).also {
                        channel.basicPublish(exchange, keyOut, null, it)
                    }
                }
            }
        }
    }

    override fun Channel.getCancelCallback() = CancelCallback {
        println("[$it] was cancelled")
    }

    override fun Channel.listen(deliverCallback: DeliverCallback, cancelCallback: CancelCallback) {
        exchangeDeclare(exchange, "direct")
        queueDeclare(queue, false, false, false, null)
        queueBind(queue, exchange, keyIn)
        basicConsume(queue, true, consumerTag, deliverCallback, cancelCallback)
        while (isOpen) {
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        println("Channel for [$consumerTag] was closed.")
    }
}

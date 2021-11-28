import com.fasterxml.jackson.databind.ObjectMapper
import ru.tk.adprofiles.openapi.models.BaseMessage
import ru.tk.adprofiles.openapi.models.CreateProfileRequest
import ru.tk.adprofiles.openapi.models.CreateableProfile
import ru.tk.adprofiles.openapi.models.ProfileRole
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializationTest {
    private val requestId = "11111111-1111-1111-1111-111111111111"
    private val createRequest = CreateProfileRequest(
        requestId = requestId,
        createProfile = CreateableProfile(
            id = "111",
            firsName = "firsName",
            secondName = "secondName",
            role = ProfileRole.CUSTOMER
        )
    )
    private val om = ObjectMapper()

    @Test
    fun serializationTest() {
        val json = om.writeValueAsString(createRequest)
        println(json)
        assertTrue("json must contain discriminator") {
            json.contains(""""messageType":"${createRequest::class.simpleName}"""")
        }
        assertTrue("json must serialize visibility field") {
            json.contains(""""role":"${ProfileRole.CUSTOMER.value}"""")
        }
        assertTrue("json must serialize messageId field") {
            json.contains(""""requestId":"$requestId"""")
        }
    }

    @Test
    fun deserializeTest() {
        val json = om.writeValueAsString(createRequest)
        val deserialized = om.readValue(json, BaseMessage::class.java) as CreateProfileRequest

        assertEquals(ProfileRole.CUSTOMER, deserialized.createProfile?.role)
        assertEquals(requestId, deserialized.requestId)
    }
}

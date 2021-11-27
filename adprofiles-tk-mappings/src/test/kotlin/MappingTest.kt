import context.MpContext
import models.*
import org.junit.Test
import backend.transport.mapping.kmp.setQuery
import backend.transport.mapping.kmp.toUpdateResponse
import ru.tk.adprofiles.openapi.models.ProfileRole
import ru.tk.adprofiles.openapi.models.UpdateProfileRequest
import ru.tk.adprofiles.openapi.models.UpdateProfileResponse
import ru.tk.adprofiles.openapi.models.UpdateableProfile
import kotlin.test.assertEquals

class MappingTest {
    @Test
    fun setUpdateQueryMappingTest() {
        val query = UpdateProfileRequest(
            requestId = "12345",
            createProfile = UpdateableProfile(
                id = "11111111-1111-1111-1111-111111111id1",
                firsName = "firsName-1",
                secondName = "secondName-1",
                role = ProfileRole.CUSTOMER
            )
        )
        val context = MpContext().setQuery(query)
        assertEquals("12345", context.onRequest)
        assertEquals(query.createProfile?.id, context.requestProfile.id.asString())
        assertEquals("firsName-1", context.requestProfile.firsName)
        assertEquals("secondName-1", context.requestProfile.secondName)
        assertEquals("CUSTOMER", context.requestProfile.role.toString())
    }

    @Test
    fun updateResponseMappingTest() {
        val context = MpContext(
            onRequest = "12345",
            responseProfile = ProfileModel(
                id = ProfileIdModel("11111111-1111-1111-1111-111111111id1"),
                firsName = "firsName-1",
                secondName = "secondName-1",
                role = ProfileRoleModel.SALESMAN
            ),
            errors = mutableListOf(CommonErrorModel(level = IError.Level.WARNING)),
        )
        val response = context.toUpdateResponse()
        assertEquals("12345", response.requestId)
        assertEquals(context.responseProfile.id.asString(), response.updatedProfile?.id)
        assertEquals("firsName-1", response.updatedProfile?.firsName)
        assertEquals("secondName-1", response.updatedProfile?.secondName)
        assertEquals("salesman", response.updatedProfile?.role.toString())
        assertEquals(UpdateProfileResponse.Result.SUCCESS, response.result)
        assertEquals(1, response.errors?.size)
    }
}

package controllers

import org.junit.Test
import ru.tk.adprofiles.openapi.models.*
import test.ktor.utils.Utils
import test.ktor.utils.Utils.stubCreatableProfile
import test.ktor.utils.Utils.stubUpdateableProfile
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProfileRouterStubTest : RouterTest() {
    @Test
    fun testPostProfileCreate() {
        val data = CreateProfileRequest(createProfile = stubCreatableProfile, debug = Utils.stubSuccessDebug)

        testPostRequest<CreateProfileResponse>(data, "/profile/create") {
            assertEquals(CreateProfileResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseProfile, createdProfile)
        }
    }

    @Test
    fun testPostProfileRead() {
        val data = ReadProfileRequest(readProfileId = "99999", debug = Utils.stubSuccessDebug)

        testPostRequest<ReadProfileResponse>(data, "/profile/read") {
            assertEquals(ReadProfileResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseProfile.copy(id = "99999"), readProfile)
        }
    }

    @Test
    fun testPostProfileUpdate() {
        val data = UpdateProfileRequest(createProfile = stubUpdateableProfile, debug = Utils.stubSuccessDebug)

        testPostRequest<UpdateProfileResponse>(data, "/profile/update") {
            assertEquals(UpdateProfileResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseProfile, updatedProfile)
        }
    }

    @Test
    fun testPostProfileDelete() {
        val data = DeleteProfileRequest(deleteProfileId = "98765", debug = Utils.stubSuccessDebug)

        testPostRequest<DeleteProfileResponse>(data, "/profile/delete") {
            assertEquals(DeleteProfileResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }
}

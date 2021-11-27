package test.ktor.utils

import ProfileStub
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.tk.adprofiles.openapi.models.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object Utils {
    val mapper = jacksonObjectMapper()

    fun <T : List<*>> assertListEquals(expected: T, actual: T, checkOrder: Boolean, message: String? = null) {
        if (checkOrder) {
            assertEquals(expected, actual, message)
        } else {
            assertTrue(
                expected.size == actual.size && expected.containsAll(actual) && actual.containsAll(expected),
                "Expected equal unordered list <$expected>, actual <$actual>."
            )
        }
    }

    val stubSuccessDebug = BaseDebugRequest(mode = BaseDebugRequest.Mode.STUB, stubCase = BaseDebugRequest.StubCase.SUCCESS)

    val stubResponseProfile = ResponseProfile(
        firsName = ProfileStub.getModel().firsName,
        secondName = ProfileStub.getModel().secondName,
        role = ProfileRole.valueOf(ProfileStub.getModel().role.toString()),
        id = ProfileStub.getModel().id.asString(),
    )

    val stubCreatableProfile = CreateableProfile(
        firsName = stubResponseProfile.firsName,
        secondName = stubResponseProfile.secondName,
        role = stubResponseProfile.role,
    )

    val stubUpdateableProfile = UpdateableProfile(
        firsName = stubResponseProfile.firsName,
        secondName = stubResponseProfile.secondName,
        role = stubResponseProfile.role,
        id = stubResponseProfile.id,
    )
}

import models.ProfileIdModel
import models.ProfileModel
import models.ProfileRoleModel

object ProfileStub {
    private val stubReady = ProfileModel(
        id = ProfileIdModel(id = "666"),
        firsName = "firsName CUSTOMER",
        secondName = "secondName CUSTOMER",
        role = ProfileRoleModel.CUSTOMER
    )

    private val stubInProgrss = ProfileModel(
        id = ProfileIdModel(id = "12345678"),
        firsName = "firsName SALESMAN",
        secondName = "secondName SALESMAN",
        role = ProfileRoleModel.SALESMAN
    )

    fun getModel(model: (ProfileModel.() -> Unit)? = null) = stubReady.also { stub ->
        model?.let { stub.apply(it) }
    }

    fun isCorrectId(id: String) = id == "666"

    fun getModels() = listOf(
        stubReady,
        stubInProgrss
    )

    fun ProfileModel.update(updateableProfile: ProfileModel) = apply {
        firsName = updateableProfile.firsName
        secondName = updateableProfile.secondName
        role = updateableProfile.role
    }
}

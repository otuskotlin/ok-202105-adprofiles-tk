package repo.test

import models.ProfileIdModel
import models.ProfileModel
import models.ProfileRoleModel
import java.util.*

abstract class BaseInitProfiles : IInitObjects<ProfileModel> {

    fun createInitTestModel(
        suf: String
    ) = ProfileModel(
        id = ProfileIdModel(UUID.randomUUID()),
        firsName = "$suf stub firsName",
        secondName = "$suf stub secondName",
        role = ProfileRoleModel.CUSTOMER
    )
}

package repo

import models.ProfileModel

data class DbProfileModelRequest(
    val profile: ProfileModel
): IDbRequest

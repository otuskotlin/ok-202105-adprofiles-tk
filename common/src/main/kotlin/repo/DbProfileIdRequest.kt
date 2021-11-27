package repo

import models.ProfileIdModel


data class DbProfileIdRequest(
    val id: ProfileIdModel
): IDbRequest

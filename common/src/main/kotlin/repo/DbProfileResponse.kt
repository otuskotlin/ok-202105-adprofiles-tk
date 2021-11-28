package repo

import models.CommonErrorModel
import models.ProfileModel

data class DbProfileResponse(
    override val result: ProfileModel?,
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList()
) : IDbResponse<ProfileModel?> {
    constructor(result: ProfileModel) : this(result, true)
    constructor(error: CommonErrorModel) : this(null, false, listOf(error))
}

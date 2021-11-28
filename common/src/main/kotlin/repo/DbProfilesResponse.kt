package repo

import models.CommonErrorModel
import models.ProfileModel

data class DbProfilesResponse(
    override val result: List<ProfileModel>,
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList()
) : IDbResponse<List<ProfileModel>> {
    constructor(result: List<ProfileModel>) : this(result, true)
    constructor(error: CommonErrorModel) : this(emptyList(), false, listOf(error))
}

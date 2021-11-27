package repo.inmemory.models


import models.*
import java.io.Serializable

data class ProfileRow(
    val id: String? = null,
    val firsName: String? = null,
    val secondName: String? = null,
    val role: String? = null
): Serializable {
    constructor(internal: ProfileModel): this(
        id = internal.id.asString().takeIf { it.isNotBlank() },
        firsName = internal.firsName.takeIf { it.isNotBlank() },
        secondName = internal.secondName.takeIf { it.isNotBlank() },
        role = internal.role.takeIf { it != ProfileRoleModel.NONE }?.name,
    )

    fun toInternal(): ProfileModel = ProfileModel(
        id = id?.let { ProfileIdModel(it) } ?: ProfileIdModel.NONE,
        firsName = firsName ?: "",
        secondName = secondName ?: "",
        role = role?.let { ProfileRoleModel.valueOf(it) } ?: ProfileRoleModel.NONE,
    )
}

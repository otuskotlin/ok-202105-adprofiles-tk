package models

data class ProfileModel(
    var id: ProfileIdModel = ProfileIdModel.NONE,
    var firsName: String = "",
    var secondName: String = "",
    var role: ProfileRoleModel = ProfileRoleModel.NONE
)

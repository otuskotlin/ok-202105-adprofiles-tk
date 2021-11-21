package model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.OffsetDateTime

data class User (
    @field:JsonProperty("id")
    var id: String = "",
    @field:JsonProperty("email")
    var email: String = "",
    @field:JsonProperty("phone")
    var phone: String = "",
    @field:JsonProperty("name")
    var name: String = "",
    @field:JsonProperty("surname")
    var surname: String = "",
    @field:JsonProperty("secondName")
    var secondName: String = "",
    @field:JsonProperty("birthday")
    var birthday: LocalDate = LocalDate.now(),
    @field:JsonProperty("registrationDate")
    var registrationDate: OffsetDateTime = OffsetDateTime.now(),
    /* Url фотографии пользователя */
    @field:JsonProperty("photo")
    var photo: List<String> = mutableListOf(),
    @field:JsonProperty("city")
    var city: String = ""
)


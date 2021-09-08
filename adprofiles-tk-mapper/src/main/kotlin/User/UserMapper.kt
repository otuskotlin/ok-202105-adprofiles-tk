package User

import models.User
import ru.tk.adprofiles.kmp.transport.models.UserDto
import java.time.OffsetDateTime

fun User.toUserDto() = UserDto(
    id = this.id,
    email = this.email,
    phone = this.phone,
    name = this.name,
    surname = this.surname,
    secondName = this.secondName,
    birthday = this.birthday.toString(),
    registrationDate = this.registrationDate.toString(),
    photo = this.photo,
    city = this.city
)

fun UserDto.toUser() = User(
    id = this.id ?: "",
    email = this.email?: "",
    phone = this.phone?: "",
    name = this.name?: "",
    surname = this.surname?: "",
    secondName = this.secondName?: "",
    birthday = OffsetDateTime.parse(this.birthday),
    registrationDate = OffsetDateTime.parse(this.registrationDate),
    photo = this.photo?:mutableListOf(),
    city = this.city?: ""
)

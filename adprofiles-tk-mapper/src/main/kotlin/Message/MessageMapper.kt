package Message

import User.toUser
import User.toUserDto
import models.Message
import models.User
import ru.tk.adprofiles.kmp.transport.models.MessageDto
import java.time.OffsetDateTime


fun Message.toMessageDto() = MessageDto(
    this.id,
    this.title,
    this.description,
    this.dateSend.toString(),
    this.dateRead.toString(),
    this.category,
    this.from.toUserDto()
)

fun MessageDto.toMessage() = Message(
    this.id ?: "",
    this.title ?: "",
    this.description ?: "",
    OffsetDateTime.parse(this.dateSend),
    OffsetDateTime.parse(this.dateRead),
    this.category ?: "",
    this.from?.toUser() ?: User()
)

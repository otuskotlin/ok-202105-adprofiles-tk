package Message

import User.toUser
import User.toUserDto
import models.Message
import models.User
import ru.tk.adprofiles.kmp.transport.models.MessageDto
import java.time.OffsetDateTime


fun Message.toMessageDto() = MessageDto(
    id = this.id,
    title = this.title,
    description = this.description,
    dateSend = this.dateSend.toString(),
    dateRead = this.dateRead.toString(),
    category = this.category,
    from = this.from.toUserDto()
)

fun MessageDto.toMessage() = Message(
    id = this.id ?: "",
    title = this.title ?: "",
    description = this.description ?: "",
    dateSend = OffsetDateTime.parse(this.dateSend),
    dateRead = OffsetDateTime.parse(this.dateRead),
    category = this.category ?: "",
    from = this.from?.toUser() ?: User()
)

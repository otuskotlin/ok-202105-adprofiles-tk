package Ad

import models.Ad
import ru.tk.adprofiles.kmp.transport.models.AdDto
import java.time.OffsetDateTime


fun Ad.toAdDto() = AdDto(
    id = this.id,
    title = this.title,
    description = this.description,
    photo = this.photo,
    dateBegin = this.dateBegin.toString(),
    dateEnd = this.dateEnd.toString()
)

fun AdDto.toAd() = Ad(
    id = this.id ?: "",
    title = this.title ?: "",
    description = this.description ?: "",
    photo = this.photo ?: mutableListOf(),
    dateBegin = OffsetDateTime.parse(this.dateBegin),
    dateEnd = OffsetDateTime.parse(this.dateEnd)
)

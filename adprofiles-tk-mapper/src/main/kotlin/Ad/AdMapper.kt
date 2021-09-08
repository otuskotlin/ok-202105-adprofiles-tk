package Ad

import models.Ad
import ru.tk.adprofiles.kmp.transport.models.AdDto
import java.time.OffsetDateTime


fun Ad.toAdDto() = AdDto(
    this.id,
    this.title,
    this.description,
    this.photo,
    this.dateBegin.toString(),
    this.dateEnd.toString()
)

fun AdDto.toAd() = Ad(
    this.id ?: "",
    this.title ?: "",
    this.description ?: "",
    this.photo ?: mutableListOf(),
    OffsetDateTime.parse(this.dateBegin),
    OffsetDateTime.parse(this.dateEnd)
)

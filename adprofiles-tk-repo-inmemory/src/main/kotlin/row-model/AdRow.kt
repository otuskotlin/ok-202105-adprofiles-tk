package row

import model.Ad
import java.io.Serializable
import java.time.OffsetDateTime

data class AdRow(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val photo: List<String>? = null,
    val dateBegin: String? = null,
    val dateEnd: String? = null
) : Serializable {
    constructor(internal: Ad) : this(
        id = internal.id.takeIf { it.isNotBlank() },
        title = internal.title.takeIf { it.isNotBlank() },
        description = internal.description.takeIf { it.isNotBlank() },
        photo = internal.photo.takeIf { it.isNotEmpty() },
        dateBegin = internal.dateBegin.toString().takeIf { it.isNotBlank() },
        dateEnd = internal.dateEnd.toString().takeIf { it.isNotBlank() },
    )

    fun toInternal(): Ad = Ad(
        id = this.id ?: "",
        title = this.title ?: "",
        description = this.description ?: "",
        photo = this.photo ?: mutableListOf(),
        dateBegin = OffsetDateTime.parse(this.dateBegin),
        dateEnd = OffsetDateTime.parse(this.dateEnd)
    )
}

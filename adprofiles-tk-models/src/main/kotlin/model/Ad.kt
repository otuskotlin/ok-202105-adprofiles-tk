package model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Ad (
    @field:JsonProperty("id")
    var id: String = "",
    @field:JsonProperty("title")
    var title: String = "",
    @field:JsonProperty("description")
    var description: String = "",
    @field:JsonProperty("photo")
    var photo: List<String> = mutableListOf(),
    @field:JsonProperty("dateBegin")
    var dateBegin: OffsetDateTime = OffsetDateTime.now(),
    @field:JsonProperty("dateEnd")
    var dateEnd: OffsetDateTime = OffsetDateTime.now()
)

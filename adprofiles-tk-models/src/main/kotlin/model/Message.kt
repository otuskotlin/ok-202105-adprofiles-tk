package model


import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Message (
    @field:JsonProperty("id")
    var id: String = "",
    /* Заголовок сообщения */
    @field:JsonProperty("title")
    var title: String = "",
    /* Описание сообщения */
    @field:JsonProperty("description")
    var description: String = "",
    /* Дата отправки */
    @field:JsonProperty("dateSend")
    var dateSend: OffsetDateTime = OffsetDateTime.now(),
    /* Дата прочтения */
    @field:JsonProperty("dateRead")
    var dateRead: OffsetDateTime = OffsetDateTime.now(),
    /* Категория */
    @field:JsonProperty("category")
    var category: String = "",
    @field:JsonProperty("from")
    var from: User = User()
)


package models

import java.util.*


@JvmInline
value class ProfileIdModel(private val id: String) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = ProfileIdModel("")
    }

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}

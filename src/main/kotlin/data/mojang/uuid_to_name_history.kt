package com.xtex.mirai.minecraft.data.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias UUIDToNameHistoryResponse = Array<UUIDToNameHistoryElement>

@Serializable
data class UUIDToNameHistoryElement(
    @SerialName("name")
    val name: String,
    @SerialName("changedToAt")
    val changedToAt: Long? = null
)

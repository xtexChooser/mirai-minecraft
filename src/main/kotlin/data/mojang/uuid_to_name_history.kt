package com.xtex.mirai.minecraft.data.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias UUIDToNameHistoryResponse = Array<UUIDToNameHistoryElement>

@Serializable
data class UUIDToNameHistoryElement(
    val name: String,
    val changedToAt: Long? = null
)

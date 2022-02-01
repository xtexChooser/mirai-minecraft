package com.xtex.mirai.minecraft.data.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsernameToUUIDResponse(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: String,
    @SerialName("legacy")
    val legacy: Boolean = false,
    @SerialName("demo")
    val demo: Boolean = false,
)

package com.xtex.mirai.minecraft.data.mojang

import kotlinx.serialization.Serializable

@Serializable
data class UsernameToUUIDResponse(
    val name: String,
    val id: String,
    val legacy: Boolean = false,
    val demo: Boolean = false,
)

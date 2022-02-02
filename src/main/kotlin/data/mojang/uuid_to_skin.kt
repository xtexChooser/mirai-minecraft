package com.xtex.mirai.minecraft.data.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UUIDToSkinResponse(
    val id: String,
    val name: String,
    val legacy: Boolean = false,
    val properties: List<UUIDToSkinProperty>
) {

    operator fun get(key: String) = properties.find { it.name == key }!!.value

}

@Serializable
data class UUIDToSkinProperty(
    val name: String,
    val value: String,
)

@Serializable
data class TexturesProperty(
    val timestamp: Long,
    val profileId: String,
    val profileName: String,
    val signatureRequired: Boolean = false,
    val textures: Map<String, Texture>,
)

@Serializable
data class Texture(
    val url: String,
    val metadata: TextureMetadata? = null,
)

@Serializable
data class TextureMetadata(
    val model: String,
)

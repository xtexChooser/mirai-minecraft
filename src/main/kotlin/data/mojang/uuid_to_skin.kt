package com.xtex.mirai.minecraft.data.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UUIDToSkinResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("legacy")
    val legacy: Boolean = false,
    @SerialName("properties")
    val properties: List<UUIDToSkinProperty>
) {

    operator fun get(key: String) = properties.find { it.name == key }!!.value

}

@Serializable
data class UUIDToSkinProperty(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val value: String,
)

@Serializable
data class TexturesProperty(
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("profileId")
    val profileId: String,
    @SerialName("profileName")
    val profileName: String,
    @SerialName("signatureRequired")
    val signatureRequired: Boolean = false,
    @SerialName("textures")
    val textures: Map<String, Texture>,
)

@Serializable
data class Texture(
    @SerialName("url")
    val url: String,
    @SerialName("metadata")
    val metadata: TextureMetadata? = null,
)

@Serializable
data class TextureMetadata(
    @SerialName("model")
    val model: String,
)

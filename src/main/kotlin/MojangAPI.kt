package com.xtex.mirai.minecraft

import com.xtex.mirai.minecraft.data.mojang.UUIDToNameHistoryResponse
import com.xtex.mirai.minecraft.data.mojang.UUIDToSkinResponse
import com.xtex.mirai.minecraft.data.mojang.UsernameToUUIDResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.utils.info
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

object MojangAPI {

    private val httpClient: OkHttpClient = OkHttpClient.Builder().build()

    @OptIn(ExperimentalSerializationApi::class)
    @Throws(IllegalStateException::class)
    fun callUsernameToUUID(username: String) : UsernameToUUIDResponse? {
        val response = httpClient.newCall(
            Request.Builder()
            .url("https://api.mojang.com/users/profiles/minecraft/$username")
            .get()
            .build())
            .execute()
        return if(response.isSuccessful) {
            if(response.body != null && response.code != 204) {
                Json.decodeFromString(response.body!!.string())
            }else {
                null // User not found
            }
        } else {
            throw IllegalStateException("Got $response with body ${response.body?.string()}")
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun callUUIDToNameHistory(uuid: String) : UUIDToNameHistoryResponse {
        val response = httpClient.newCall(
            Request.Builder()
                .url("https://api.mojang.com/user/profiles/$uuid/names")
                .get()
                .build())
            .execute()
        MinecraftPlugin.logger.info { "Got $response for $uuid when UUID to name history" }
        return Json.decodeFromString(response.body!!.string())
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun callUUIDToSkin(uuid: String) : UUIDToSkinResponse {
        val response = httpClient.newCall(
            Request.Builder()
                .url("https://sessionserver.mojang.com/session/minecraft/profile/$uuid")
                .get()
                .build())
            .execute()
        MinecraftPlugin.logger.info { "Got $response for $uuid when UUID to skin" }
        return Json.decodeFromString(response.body!!.string())
    }

    fun fetchURL(url: String) : ResponseBody {
        val response = httpClient.newCall(
            Request.Builder()
                .url(url)
                .get()
                .build())
            .execute()
        MinecraftPlugin.logger.info { "Got $response for $url when fetching URL" }
        return response.body!!
    }

}
package com.xtex.mirai.minecraft.command

import com.xtex.mirai.minecraft.MinecraftPlugin
import com.xtex.mirai.minecraft.MojangAPI
import com.xtex.mirai.minecraft.data.mojang.TexturesProperty
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.message.data.ForwardMessage
import net.mamoe.mirai.message.data.RawForwardMessage
import net.mamoe.mirai.message.data.buildForwardMessage
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import okio.ByteString.Companion.decodeBase64
import java.nio.charset.StandardCharsets
import java.time.format.DateTimeFormatter
import java.util.*

object MinecraftCommand : CompositeCommand(
    MinecraftPlugin, "minecraft", "mc",
    description = "获取有关Minecraft的信息"
) {

    @OptIn(ExperimentalSerializationApi::class)
    @SubCommand("player", "user")
    @Description("查询玩家信息")
    suspend fun CommandSender.playerInfo(username: String) {
        if (subject == null) {
            sendMessage("需要聊天环境")
            return
        }
        try {
            val usernameToUUIDResponse = MojangAPI.callUsernameToUUID(username)
            if (usernameToUUIDResponse == null) {
                sendMessage("玩家 $username 不存在")
            } else {
                val uuid = usernameToUUIDResponse.id
                val skinProperties = MojangAPI.callUUIDToSkin(uuid)
                val nameHistory = MojangAPI.callUUIDToNameHistory(uuid)
                sendMessage(buildForwardMessage(subject!!, PlayerInfoDisplayStrategy(username)) {
                    add(bot!!, buildMessageChain {
                        +"===Minecraft 玩家信息===\n"
                        +"玩家：$username\n"
                        +"UUID：$uuid\n"
                        if (usernameToUUIDResponse.demo)
                            +"* 该账户是Demo账户（未购买Minecraft）\n"
                        if (usernameToUUIDResponse.legacy)
                            +"* 该账户是Legacy账户（未迁移至mojang.com）\n"
                    })
                    val textures: TexturesProperty = Json.decodeFromString(
                        skinProperties["textures"].decodeBase64()!!.string(StandardCharsets.UTF_8)
                    )
                    add(bot!!, buildMessageChain {
                        val skinProperty = textures.textures["SKIN"]!!
                        +"皮肤链接：${skinProperty.url}\n"
                        +"皮肤模型：${skinProperty.metadata?.model}（${if (skinProperty.metadata?.model == "slim") "纤细" else "方臂"}）\n"
                        +MojangAPI.fetchURL(skinProperty.url).byteStream().uploadAsImage(subject!!)
                    })
                    add(bot!!, buildMessageChain {
                        if ("CAPE" !in textures.textures) {
                            +"该用户无使用中的披风"
                        } else {
                            val capeProperty = textures.textures["CAPE"]!!
                            +"披风链接：${capeProperty.url}\n"
                            +MojangAPI.fetchURL(capeProperty.url).byteStream().uploadAsImage(subject!!)
                        }
                    })
                    add(bot!!, buildMessageChain {
                        +"历史用户名：\n"
                        nameHistory.reversed().forEach {
                            +"- ${it.name} (${
                                if (it.changedToAt != null)
                                    DateTimeFormatter.ISO_INSTANT.format(Date(it.changedToAt).toInstant())
                                else "注册名"
                            })\n"
                        }
                    })
                })
            }
        } catch (e: IllegalArgumentException) {
            sendMessage("获取 $username 的用户信息失败")
            e.printStackTrace()
        }
    }

    class PlayerInfoDisplayStrategy(private val username: String) : ForwardMessage.DisplayStrategy {

        override fun generateTitle(forward: RawForwardMessage): String = "$username 的Minecraft玩家信息"

        override fun generateBrief(forward: RawForwardMessage): String = "[Minecraft玩家信息]"

        override fun generateSummary(forward: RawForwardMessage): String = "查看 $username 的玩家信息"

    }

}
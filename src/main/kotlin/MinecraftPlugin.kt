package com.xtex.mirai.minecraft

import com.xtex.mirai.minecraft.command.MinecraftCommand
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info
import okhttp3.OkHttpClient
import okhttp3.Request

object MinecraftPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "com.xtex.mirai-minecraft",
        name = "Minecraft Support",
        version = MinecraftPlugin::class.java.`package`.implementationVersion
    ) {
        author("xtexChooser")
    }
) {

    override fun onEnable() {
        CommandManager.registerCommand(MinecraftCommand)
    }

}
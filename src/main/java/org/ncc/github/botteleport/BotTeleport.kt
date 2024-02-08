package org.ncc.github.botteleport

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull
import org.ncc.github.botteleport.command.commandBotTp
import java.util.logging.Level
import java.util.logging.Logger

class BotTeleport : JavaPlugin() {


    private var configVer = 1

    companion object {
        var l: Logger? = null
        var mistakeStr: String? = null
        var successStr: String? = null
        var nopermissionStr: String? = null
        var notPlayerStr: String? = null
        var notSameWorldStr: String? = null
        var reloadMsgStr: String? = null
        var notExistStr: String? = null

        var plugin: JavaPlugin? = null
        fun configReload(s: String): String {
            plugin?.reloadConfig()
            loadConfig(plugin!!.config, plugin!!)
            return s
        }

        private fun loadConfig(
            @NotNull f: FileConfiguration,
            @NotNull p: JavaPlugin
        ) {
            if (f.getString("mistake") != null) mistakeStr = f.getString("mistake")!!
            if (f.getString("success") != null) successStr = f.getString("success")!!
            if (f.getString("notplayer") != null) notPlayerStr = f.getString("notplayer")!!
            if (f.getString("notsameworld") != null) notSameWorldStr = f.getString("notsameworld")!!
            if (f.getString("notexist") != null) notExistStr = f.getString("notexist")!!
            if (f.getString("nopermission") != null) nopermissionStr = f.getString("nopermission")!!
            if (f.getString("reloadMsg") != null) reloadMsgStr = f.getString("reloadMsg")!!
        }
    }

    override fun onEnable() {
        plugin = this
        l = plugin!!.logger
        l!!.info("BotTeleport plugin enabling")
        checkConfig(config, plugin!!)
        loadConfig(config, plugin!!)
        getCommand("bottp")?.setExecutor(commandBotTp())
        getCommand("bottp")?.tabCompleter = commandBotTp()

    }

    override fun onDisable() {
        l!!.info("BotTeleport plugin disabling")
        saveConfig()
        getCommand("bottp")?.setExecutor(null)
    }


    private fun checkConfig(@NotNull f: FileConfiguration, @NotNull p: JavaPlugin) {
        if (f.get("config-version") != null) {
            if (f.getInt("config-version") != configVer) {
                p.logger.log(Level.SEVERE, "BotTeleport配置文件出错，请检查，或删除其重新生成")
                p.logger.log(
                    Level.SEVERE,
                    "The config of BotTeleport went wrong,please check,or just delete it to regenerate new config file"
                )
                p.onDisable()
            }
        } else {
            f.set("config-version", configVer)
        }
        if (f.get("mistake") == null) f.set("mistake", "指令使用方法有误")
        if (f.get("success") == null) f.set("success", "假人传送成功")
        if (f.get("notplayer") == null) f.set("notplayer", "您不是一个玩家")
        if (f.get("notsameworld") == null) f.set(
            "notsameworld",
            "您与机器人不在同一个维度，该指令只能传送同一维度的假人到您身边"
        )
        if (f.get("notexist") == null) f.set("notexist", "机器人不存在")
        if (f.get("nopermission") == null) f.set("notpermission", "你没有权限")
        if (f.get("reloadMsg") == null) f.set("reloadMsg", "重载配置文件成功")
        p.saveConfig()
    }


}

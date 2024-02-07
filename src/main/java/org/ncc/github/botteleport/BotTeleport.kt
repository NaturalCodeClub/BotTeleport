package org.ncc.github.botteleport

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull
import org.ncc.github.botteleport.command.commandBotTp
import java.util.logging.Level
import java.util.logging.Logger

class BotTeleport : JavaPlugin() {


    var config: FileConfiguration? = null
    var configVer = 1

    companion object {
        lateinit var l: Logger
        lateinit var mistakeStr: String
        lateinit var successStr: String
        lateinit var nopermissionStr: String
        lateinit var notPlayerStr: String
        lateinit var reloadMsgStr: String

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
            if (f.getString("nopermission") != null) nopermissionStr = f.getString("nopermission")!!
            if (f.getString("reloadMsg") != null) reloadMsgStr = f.getString("reloadMsg")!!
        }
    }

    override fun onEnable() {
        l.info("BotTeleport plugin enabling")
        plugin = getPlugin(this)
        config = getConfig()
        l = plugin!!.logger
        checkConfig(config!!, plugin!!)
        loadConfig(config!!, plugin!!)
        getCommand("bottp")?.setExecutor(commandBotTp())
        getCommand("bottp")?.tabCompleter = commandBotTp()

    }

    override fun onDisable() {
        l.info("BotTeleport plugin disabling")
        saveConfig()
        getCommand("bottp")?.setExecutor(null)
    }

    private fun getPlugin(plugin: JavaPlugin): JavaPlugin {
        return plugin
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
        if (f.get("nopermission") == null) f.set("notpermission", "你没有权限")
        if (f.get("reloadMsg") == null) f.set("reloadMsg", "重载配置文件成功")
        p.saveConfig()
    }


}

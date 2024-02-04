package org.ncc.github.botteleport

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.ncc.github.botteleport.command.commandBotTp
import java.util.logging.Logger

class BotTeleport : JavaPlugin() {
    @JvmField val l:Logger = logger
    @JvmField
    var plugin: JavaPlugin? = null
    override fun onEnable() {
        l.info("BotTeleport plugin enabling")
        val c = commandBotTp()
        getCommand("bottp")?.setExecutor(c)
        getCommand("bottp")?.tabCompleter = c
        plugin = getPlugin(this)
    }

    override fun onDisable() {
        l.info("BotTeleport plugin disabling")
        getCommand("bottp")?.setExecutor(null)
    }
    private fun getPlugin(plugin: JavaPlugin): JavaPlugin {
        return plugin
    }



}

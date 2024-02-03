package org.ncc.github.botteleport

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class BotTeleport : JavaPlugin() {
    @JvmField val l:Logger = logger
    @JvmField
    var plugin: JavaPlugin? = null
    override fun onEnable() {
        l.info("BotTeleport plugin enabling")
        plugin = getPlugin(this)
    }

    override fun onDisable() {
        l.info("BotTeleport plugin disabling")
    }
    private fun getPlugin(plugin: JavaPlugin): JavaPlugin {
        return plugin
    }



}

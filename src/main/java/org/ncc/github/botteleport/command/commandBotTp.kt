package org.ncc.github.botteleport.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.ncc.github.botteleport.BotTeleport
import org.ncc.github.botteleport.BotTeleport.Companion.configReload
import org.ncc.github.botteleport.BotTeleport.Companion.mistakeStr
import org.ncc.github.botteleport.BotTeleport.Companion.nopermissionStr
import org.ncc.github.botteleport.BotTeleport.Companion.notExistStr
import org.ncc.github.botteleport.BotTeleport.Companion.notPlayerStr
import org.ncc.github.botteleport.BotTeleport.Companion.notSameWorldStr
import org.ncc.github.botteleport.BotTeleport.Companion.reloadMsgStr
import org.ncc.github.botteleport.BotTeleport.Companion.successStr
import top.leavesmc.leaves.entity.Bot

class commandBotTp : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        str: String,
        args: Array<out String>?
    ): MutableList<String>? {
        val t: MutableList<String> = mutableListOf()
        Bukkit.getBotManager().bots.forEach { bot ->
            bot.player?.let { t.add(it.name) }
        }
        return t
    }

    override fun onCommand(sender: CommandSender, command: Command, p2: String, args: Array<out String>?): Boolean {
        if (!sender.hasPermission("bukkit.command.bot")) {
            nopermissionStr?.let { sender.sendMessage(it) }
            return true
        }
        if (sender !is Player) {
            notPlayerStr?.let { sender.sendMessage(it) }
            return false
        }
        if (args!!.size != 1) {
            mistakeStr?.let { sender.sendMessage(it) }
            return false
        }
        //上述判断阻止非法的指令使用方法导致插件出现问题
        val s: String = args[0]
        if (s.equals("reload", true)) {
            val s = reloadMsgStr?.let { configReload(it) }
            sender.sendMessage(s!!)
        }
        val b: Bot? = Bukkit.getBotManager().getBot(s)
        if (b == null) {
            notExistStr?.let { sender.sendMessage(it) }
            return false
        }
        if (sender.world != b.world) {
            notSameWorldStr?.let { sender.sendMessage(it) }
        }

        b.teleport(sender)

        successStr?.let { sender.sendMessage(it) }
        return true
    }

}

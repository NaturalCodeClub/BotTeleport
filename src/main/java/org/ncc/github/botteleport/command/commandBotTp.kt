package org.ncc.github.botteleport.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.ncc.github.botteleport.BotTeleport
import org.ncc.github.botteleport.BotTeleport.Companion.configReload
import org.ncc.github.botteleport.BotTeleport.Companion.reloadMsgStr
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
            sender.sendMessage("你没有权限使用这条命令")
            return true
        }
        if (sender !is Player) {
            sender.sendMessage("您不是一个玩家")
            return false
        }
        if (args!!.size != 2) {
            sender.sendMessage("指令使用方法有误")
            return false
        }
        //上述判断阻止非法的指令使用方法导致插件出现问题
        val s: String = args[1]
        if (s.equals("reload", true)) {
            sender.sendMessage(configReload(reloadMsgStr))
        }
        val b: Bot? = Bukkit.getBotManager().getBot(s)
        if (b == null) {
            sender.sendMessage("机器人不存在")
            return false
        }

        b.teleport(sender)

        sender.sendMessage("传送成功")
        return true
    }

}

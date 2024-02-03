package org.ncc.github.botteleport.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import top.leavesmc.leaves.entity.BotManager

class commandBotTp : CommandExecutor{
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): Boolean {
        if (p0 !is Player){
            p0.sendMessage("您不是一个玩家")
            return false
        }
        if (p3!!.size!=2){
            p0.sendMessage("指令使用方法有误")
            return false
        }
        //上述判断阻止非法的指令使用方法导致插件出现问题
        val s:String = p3[1]

        return true
    }

}

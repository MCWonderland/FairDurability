package io.fairy.durability

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class FairDurabilityPlugin: JavaPlugin() {

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(DurabilityHandler, this)
    }

}
package io.fairy.durability

import io.fairy.durability.extension.durabilityLevel
import io.fairy.durability.extension.floatDurability
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemDamageEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.floor

object DurabilityHandler : Listener {

    /**
     * level 1 = 50% = 0.5
     * level 2 = 33% = 0.333
     * level 3 = 25% = 0.25
     */
    private fun getReduction(level: Int): Float {
        return 1f / (level + 1)
    }

    fun handleItemDamage(item: ItemStack, damage: Int): Float {
        val current = item.floatDurability
        val reduction = getReduction(item.durabilityLevel)
        val new = current + damage * reduction
        item.floatDurability = new

        return new
    }

    @EventHandler
    fun onPlayerItemDamage(event: PlayerItemDamageEvent) {
        val item = event.item
        val damage = event.damage

        println("${item.type} $damage")
        if (item.durabilityLevel > 0) {
            val new = handleItemDamage(item, damage)
            if (new >= item.type.maxDurability) {
                event.damage = item.type.maxDurability.toInt()
            } else {
                event.isCancelled = true
                item.durability = floor(new).toInt().toShort()
            }
        }
    }

}
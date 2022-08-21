package io.fairy.durability.extension

import io.fairy.durability.api.FairDurabilityAPI
import org.bukkit.inventory.ItemStack

val ItemStack.durabilityLevel: Int
    get() = FairDurabilityAPI.getDurabilityLevel(this)

var ItemStack.floatDurability: Float
    get() = FairDurabilityAPI.getDurability(this)
    set(value) {
        FairDurabilityAPI.setDurability(this, value)
    }
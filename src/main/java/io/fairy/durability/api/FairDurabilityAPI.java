package io.fairy.durability.api;

import io.fairy.durability.util.ItemNBTEditor;
import io.fairy.durability.util.NBTEditor;
import org.bukkit.inventory.ItemStack;

public class FairDurabilityAPI {

    private static final String levelKey = "fairyDurabilityLevel";

    private static final String valueKey = "fairyDurability";

    private FairDurabilityAPI() {
    }

    public static float getDurability(ItemStack itemStack) {
        if (NBTEditor.contains(itemStack, valueKey))
            return NBTEditor.getFloat(itemStack, valueKey);
        return 0;
    }

    public static void setDurability(ItemStack itemStack, float durability) {
        ItemNBTEditor.set(itemStack, durability, valueKey);
    }

    public static int getDurabilityLevel(ItemStack itemStack) {
        if (NBTEditor.contains(itemStack, levelKey))
            return NBTEditor.getInt(itemStack, levelKey);
        return 0;
    }

    public static ItemStack setDurabilityLevel(ItemStack itemStack, int level) {
        return NBTEditor.set(itemStack, level, levelKey);
    }

}

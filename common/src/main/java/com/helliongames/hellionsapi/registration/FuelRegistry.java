package com.helliongames.hellionsapi.registration;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.level.ItemLike;

public class FuelRegistry {
    private static final Object2IntMap<ItemLike> REGISTRY = new Object2IntLinkedOpenHashMap<>();

    public static int getCookTime(ItemLike item) {
        return REGISTRY.getOrDefault(item, 0);
    }

    public static void register(ItemLike item, int burnTime) {
        REGISTRY.put(item, burnTime);
    }

    public static Object2IntMap<ItemLike> getRegistry() {
        return REGISTRY;
    }
}

package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.LootModifierDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPILootModifierRegistry;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootPool;

public class LootModifierModuleFabric {
    public static void registerLootModifiers(String modid) {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            HellionsAPILootModifierRegistry module = HellionsAPILootModifierRegistry.getModule(modid);
            if (module == null) return;

            for (LootModifierDataHolder holder : module.getLootModifierRegistry().values()) {
                if (!holder.getTargetTable().equals(key.location())) continue;

                tableBuilder.withPool(LootPool.lootPool().add(holder.getEntry().get()));
            }
        });
    }
}

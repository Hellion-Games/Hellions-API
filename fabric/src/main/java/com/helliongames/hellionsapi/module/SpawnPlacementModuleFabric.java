package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.SpawnPlacementDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPISpawnPlacementRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;

public class SpawnPlacementModuleFabric {
    public static <T extends Mob> void registerSpawnPlacements(String modid) {
        HellionsAPISpawnPlacementRegistry module = HellionsAPISpawnPlacementRegistry.getModule(modid);
        if (module == null) return;
        for (SpawnPlacementDataHolder<?> holder : module.getSpawnPlacementRegistry()) {
            SpawnPlacements.register((EntityType<T>) holder.entityType(), holder.spawnPlacementType(), holder.heightmapType(), (SpawnPlacements.SpawnPredicate<T>) holder.spawnPredicate());
        }
    }
}

package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.SpawnPlacementDataHolder;
import net.minecraft.world.entity.Mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HellionsAPISpawnPlacementRegistry {
    private static final Map<String, HellionsAPISpawnPlacementRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPISpawnPlacementRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
    }

    /** List of all SpawnPlacementDataHolders. */
    private final List<SpawnPlacementDataHolder<?>> SPAWN_PLACEMENT_REGISTRY = new ArrayList<>();

    public <T extends Mob> SpawnPlacementDataHolder<T> register(SpawnPlacementDataHolder<T> spawnPlacementDataHolder) {
        this.SPAWN_PLACEMENT_REGISTRY.add(spawnPlacementDataHolder);
        return spawnPlacementDataHolder;
    }

    public List<SpawnPlacementDataHolder<?>> getSpawnPlacementRegistry() {
        return this.SPAWN_PLACEMENT_REGISTRY;
    }

    public static Map<String, HellionsAPISpawnPlacementRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPISpawnPlacementRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}

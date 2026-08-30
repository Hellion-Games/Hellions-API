package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class SpawnPlacementDataHolder<T extends Mob> {
    private final Supplier<EntityType<T>> entityType;
    private final SpawnPlacementType spawnPlacementType;
    private final Heightmap.Types heightmapType;
    private final SpawnPlacements.SpawnPredicate<T> spawnPredicate;

    private EntityType<T> cachedEntityType;

    public SpawnPlacementDataHolder(Supplier<EntityType<T>> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        this.entityType = entityType;
        this.spawnPlacementType = spawnPlacementType;
        this.heightmapType = heightmapType;
        this.spawnPredicate = spawnPredicate;
    }

    public static <T extends Mob> SpawnPlacementDataHolder<T> of(Supplier<EntityType<T>> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        return new SpawnPlacementDataHolder<>(entityType, spawnPlacementType, heightmapType, spawnPredicate);
    }

    public EntityType<T> entityType() {
        if (this.cachedEntityType != null) {
            return this.cachedEntityType;
        }
        this.cachedEntityType = this.entityType.get();
        return this.cachedEntityType;
    }

    public SpawnPlacementType spawnPlacementType() {
        return this.spawnPlacementType;
    }

    public Heightmap.Types heightmapType() {
        return this.heightmapType;
    }

    public SpawnPlacements.SpawnPredicate<T> spawnPredicate() {
        return this.spawnPredicate;
    }
}

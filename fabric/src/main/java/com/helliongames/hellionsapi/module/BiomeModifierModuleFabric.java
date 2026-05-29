package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.BiomeModifierDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIBiomeModifierRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.Map;
import java.util.function.Predicate;

public class BiomeModifierModuleFabric {
    public static void registerBiomeModifiers(String modid) {
        HellionsAPIBiomeModifierRegistry module = HellionsAPIBiomeModifierRegistry.getModule(modid);
        if (module == null) return;

        for (Map.Entry<ResourceLocation, BiomeModifierDataHolder> entry : module.getBiomeModifierRegistry().entrySet()) {
            BiomeModifierDataHolder holder = entry.getValue();

            Predicate<BiomeSelectionContext> selector = switch (holder.getTarget()) {
                case BiomeModifierDataHolder.BiomeTarget.Tag tag ->
                        context -> context.hasTag(tag.tag());
                case BiomeModifierDataHolder.BiomeTarget.Location location ->
                        context -> context.getBiomeKey().location().equals(location.location());
            };

            if (holder.isSpawnModifier()) {
                BiomeModifierDataHolder.SpawnData spawn = holder.getSpawnData();
                EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(spawn.getEntityType());

                if (spawn.getCharge() > 0.0 || spawn.getEnergyBudget() > 0.0) {
                    BiomeModifications.create(entry.getKey()).add(ModificationPhase.ADDITIONS, selector, context -> {
                        context.getSpawnSettings().addSpawn(
                                spawn.getCategory(),
                                new MobSpawnSettings.SpawnerData(entityType, spawn.getWeight(), spawn.getMinCount(), spawn.getMaxCount())
                        );
                        context.getSpawnSettings().setSpawnCost(
                                entityType,
                                spawn.getCharge(),
                                spawn.getEnergyBudget()
                        );
                    });
                } else {
                    BiomeModifications.addSpawn(
                            selector,
                            spawn.getCategory(),
                            entityType,
                            spawn.getWeight(),
                            spawn.getMinCount(),
                            spawn.getMaxCount()
                    );
                }
            }

            if (holder.isFeatureModifier()) {
                BiomeModifierDataHolder.FeatureData feature = holder.getFeatureData();
                BiomeModifications.addFeature(selector, feature.getStep(), feature.getFeature());
            }
        }
    }
}

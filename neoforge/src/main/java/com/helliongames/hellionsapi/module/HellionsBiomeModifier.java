package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.BiomeModifierDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIBiomeModifierRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class HellionsBiomeModifier implements BiomeModifier {
    public static final MapCodec<HellionsBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(m -> m.id)
            ).apply(instance, HellionsBiomeModifier::new)
    );

    private final ResourceLocation id;

    public HellionsBiomeModifier(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD) return;

        for (HellionsAPIBiomeModifierRegistry module : HellionsAPIBiomeModifierRegistry.getModules().values()) {
            BiomeModifierDataHolder holder = module.getBiomeModifierRegistry().get(this.id);
            if (holder == null) continue;

            boolean matches = switch (holder.getTarget()) {
                case BiomeModifierDataHolder.BiomeTarget.Tag tag ->
                        biome.is(tag.tag());
                case BiomeModifierDataHolder.BiomeTarget.Location location ->
                        biome.unwrapKey().map(k -> k.location().equals(location.location())).orElse(false);
            };

            if (!matches) continue;

            if (holder.isSpawnModifier()) {
                BiomeModifierDataHolder.SpawnData spawn = holder.getSpawnData();
                EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(spawn.getEntityType());

                builder.getMobSpawnSettings().addSpawn(spawn.getCategory(), new MobSpawnSettings.SpawnerData(entityType, spawn.getWeight(), spawn.getMinCount(), spawn.getMaxCount()));

                if (spawn.getCharge() > 0.0 || spawn.getEnergyBudget() > 0.0) {
                    builder.getMobSpawnSettings().addMobCharge(entityType, spawn.getCharge(), spawn.getEnergyBudget());
                }
            }

            if (holder.isFeatureModifier()) {
                BiomeModifierDataHolder.FeatureData feature = holder.getFeatureData();
                Registry<PlacedFeature> placedFeatures = ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(Registries.PLACED_FEATURE);

                Holder<PlacedFeature> placedFeatureHolder = placedFeatures.getHolderOrThrow(feature.getFeature());
                builder.getGenerationSettings().addFeature(feature.getStep(), placedFeatureHolder);
            }
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeModifierDataHolder {
    private final BiomeTarget target;
    private final SpawnData spawnData;
    private final FeatureData featureData;

    private BiomeModifierDataHolder(BiomeTarget target, SpawnData spawnData, FeatureData featureData) {
        this.target = target;
        this.spawnData = spawnData;
        this.featureData = featureData;
    }

    public static BiomeModifierDataHolder of(BiomeTarget target, SpawnData spawnData) {
        return new BiomeModifierDataHolder(target, spawnData, null);
    }

    public static BiomeModifierDataHolder of(BiomeTarget target, FeatureData featureData) {
        return new BiomeModifierDataHolder(target, null, featureData);
    }

    public BiomeTarget getTarget() { return target; }
    public SpawnData getSpawnData() { return spawnData; }
    public FeatureData getFeatureData() { return featureData; }

    public boolean isSpawnModifier() { return spawnData != null; }
    public boolean isFeatureModifier() { return featureData != null; }

    public static final class SpawnData {
        private final MobCategory category;
        private final ResourceLocation entityType;
        private final int weight;
        private final int minCount;
        private final int maxCount;
        private final double charge;
        private final double energyBudget;

        private SpawnData(MobCategory category, ResourceLocation entityType, int weight, int minCount, int maxCount, double charge, double energyBudget) {
            this.category = category;
            this.entityType = entityType;
            this.weight = weight;
            this.minCount = minCount;
            this.maxCount = maxCount;
            this.charge = charge;
            this.energyBudget = energyBudget;
        }

        public static SpawnData of(MobCategory category, ResourceLocation entityType, int weight, int minCount, int maxCount, double charge, double energyBudget) {
            return new SpawnData(category, entityType, weight, minCount, maxCount, charge, energyBudget);
        }

        public static SpawnData of(MobCategory category, ResourceLocation entityType, int weight, int minCount, int maxCount) {
            return new SpawnData(category, entityType, weight, minCount, maxCount, 0.0, 0.0);
        }

        public MobCategory getCategory() { return category; }
        public ResourceLocation getEntityType() { return entityType; }
        public int getWeight() { return weight; }
        public int getMinCount() { return minCount; }
        public int getMaxCount() { return maxCount; }
        public double getCharge() { return charge; }
        public double getEnergyBudget() { return energyBudget; }
    }

    public static final class FeatureData {
        private final GenerationStep.Decoration step;
        private final ResourceKey<PlacedFeature> feature;

        private FeatureData(GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature) {
            this.step = step;
            this.feature = feature;
        }

        public static FeatureData of(GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature) {
            return new FeatureData(step, feature);
        }

        public static FeatureData of(GenerationStep.Decoration step, ResourceLocation featureLocation) {
            return new FeatureData(step, ResourceKey.create(Registries.PLACED_FEATURE, featureLocation));
        }

        public GenerationStep.Decoration getStep() { return step; }
        public ResourceKey<PlacedFeature> getFeature() { return feature; }
    }

    public sealed interface BiomeTarget permits BiomeTarget.Tag, BiomeTarget.Location {

        record Tag(TagKey<Biome> tag) implements BiomeTarget {
            public static Tag of(TagKey<Biome> tag) {
                return new Tag(tag);
            }

            public static Tag of(ResourceLocation tagLocation) {
                return new Tag(TagKey.create(Registries.BIOME, tagLocation));
            }
        }

        record Location(ResourceLocation location) implements BiomeTarget {
            public static Location of(ResourceLocation location) {
                return new Location(location);
            }
        }
    }
}
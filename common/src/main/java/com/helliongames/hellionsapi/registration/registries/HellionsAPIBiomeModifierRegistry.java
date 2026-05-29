package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.BiomeModifierDataHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class HellionsAPIBiomeModifierRegistry {
    private static final Map<String, HellionsAPIBiomeModifierRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPIBiomeModifierRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
    }

    /** Map of all BiomeModifier Resource Locations to their BiomeModifierDataHolders. */
    private final Map<ResourceLocation, BiomeModifierDataHolder> BIOME_MODIFIER_REGISTRY = new HashMap<>();

    /*
       public static final BiomeModifierDataHolder EXAMPLE_SPAWN_ADDER = BiomeModifierDataHolder.of(
               BiomeModifierDataHolder.BiomeTarget.Tag.of(BiomeTags.IS_OVERWORLD),
               BiomeModifierDataHolder.SpawnData.of(
                       MobCategory.CREATURE,
                       ResourceLocation.fromNamespaceAndPath("examplemod", "example_entity"),
                       10,
                       2,
                       4)
       );

       public static final BiomeModifierDataHolder EXAMPLE_FEATURE_ADDER = BiomeModifierDataHolder.of(
               BiomeModifierDataHolder.BiomeTarget.Tag.of(BiomeTags.IS_OVERWORLD),
               BiomeModifierDataHolder.FeatureData.of(
                       GenerationStep.Decoration.UNDERGROUND_ORES,
                       ResourceLocation.fromNamespaceAndPath("examplemod", "example_ore"))
       );

       For NeoForge, the user must also create a JSON file at:
       data/<modid>/neoforge/biome_modifier/<modifier_name>.json

       {
           "type": "hellionsapi:hellions_biome_modifier",
           "id": "<modid>:<registered_name (in this case, "example_entity" or "example_ore")>"
       }
    */

    public BiomeModifierDataHolder register(String name, BiomeModifierDataHolder holder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.BIOME_MODIFIER_REGISTRY.put(id, holder);
        return holder;
    }

    public Map<ResourceLocation, BiomeModifierDataHolder> getBiomeModifierRegistry() { return BIOME_MODIFIER_REGISTRY; }

    public static Map<String, HellionsAPIBiomeModifierRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIBiomeModifierRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}
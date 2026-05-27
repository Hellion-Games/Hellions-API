package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.LootModifierDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HellionsAPILootModifierRegistry {
    private static final Map<String, HellionsAPILootModifierRegistry> MODULES = new HashMap<>();

    private final String modid;
    private final Map<ResourceLocation, LootModifierDataHolder> LOOT_MODIFIER_REGISTRY = new HashMap<>();

    /*
       public static final HellionsAPILootModifierRegistry LOOT_MODIFIER_MODULE = new HellionsAPILootModifierRegistry("examplemod");

       public static final LootModifierDataHolder EXAMPLE_LOOT_MODIFIER = LOOT_MODIFIER_MODULE.register(
           "example_loot_modifier",
           ResourceLocation.fromNamespaceAndPath("minecraft", "chests/simple_dungeon"),
           () -> LootItem.lootTableItem(Items.DIAMOND)
               .when(LootItemRandomChanceCondition.randomChance(0.5f))
       );

       The user must also create a JSON file at:
       data/<modid>/neoforge/loot_modifiers/<modifier_name>.json

       {
           "type": "hellionsapi:hellions_loot_modifier",
           "conditions": [],
           "id": "<modid>:<registered_name (in this case, "example_loot_modifier")>"
       }

     */

    public HellionsAPILootModifierRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
    }

    public LootModifierDataHolder register(String name, ResourceLocation targetTable, Supplier<LootPoolEntryContainer.Builder<?>> entry) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        LootModifierDataHolder holder = LootModifierDataHolder.of(targetTable, entry);
        this.LOOT_MODIFIER_REGISTRY.put(id, holder);
        return holder;
    }

    public Map<ResourceLocation, LootModifierDataHolder> getLootModifierRegistry() {
        return this.LOOT_MODIFIER_REGISTRY;
    }

    public static Map<String, HellionsAPILootModifierRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPILootModifierRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}
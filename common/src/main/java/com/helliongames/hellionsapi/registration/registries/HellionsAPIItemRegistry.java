package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.ItemDataHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HellionsAPIItemRegistry {
    private static final List<HellionsAPIItemRegistry> MODULES = new ArrayList<>();

    private final String modid;

    public HellionsAPIItemRegistry(String modid) {
        this.modid = modid;
        MODULES.add(this);
    }

    /** Map of all Item Resource Locations to their ItemDataHolders. */
    private final Map<ResourceLocation, ItemDataHolder<?>> ITEM_REGISTRY = new HashMap<>();

    /*
    public static final HellionsAPIItemRegistry ITEM_MODULE = new HellionsAPIItemRegistry("examplemod");

    public static final ItemDataHolder<?> EXAMPLE_ITEM = ITEM_MODULE.register("example_item", ItemDataHolder.of(() ->
                    new Item(new Item.Properties()))
            .withModel(ModelTemplates.FLAT_ITEM)
            .withTranslation("Example Item")
    );
     */

    public ItemDataHolder<?> register(String name, ItemDataHolder<?> itemDataHolder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.ITEM_REGISTRY.put(id, itemDataHolder);
        return itemDataHolder;
    }

    public Map<ResourceLocation, ItemDataHolder<?>> getItemRegistry() {
        return this.ITEM_REGISTRY;
    }

    public static List<HellionsAPIItemRegistry> getModules() {
        return MODULES;
    }
}

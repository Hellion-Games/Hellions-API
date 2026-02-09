package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.ItemDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HellionsAPIItemRegistry {
    private static final Map<String, HellionsAPIItemRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPIItemRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
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

    public <T extends Item> ItemDataHolder<T> register(String name, ItemDataHolder<T> itemDataHolder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.ITEM_REGISTRY.put(id, itemDataHolder);
        return itemDataHolder;
    }

    public Map<ResourceLocation, ItemDataHolder<?>> getItemRegistry() {
        return this.ITEM_REGISTRY;
    }

    public static Map<String, HellionsAPIItemRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIItemRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}

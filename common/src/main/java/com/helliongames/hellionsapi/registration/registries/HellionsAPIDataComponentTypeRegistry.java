package com.helliongames.hellionsapi.registration.registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class HellionsAPIDataComponentTypeRegistry {
    private static final Map<String, HellionsAPIDataComponentTypeRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPIDataComponentTypeRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid,this);
    }

    /** Map of all DataComponentType Resource Locations to their DataComponentType. */
    private final Map<ResourceLocation, DataComponentType<?>> DATA_COMPONENT_TYPE_REGISTRY = new HashMap<>();

    public <T> DataComponentType<T> register(String name, DataComponentType<T> dataComponentType) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.DATA_COMPONENT_TYPE_REGISTRY.put(id, dataComponentType);
        return dataComponentType;
    }

    public Map<ResourceLocation, DataComponentType<?>> getDataComponentTypeRegistry() {
        return this.DATA_COMPONENT_TYPE_REGISTRY;
    }

    public static Map<String, HellionsAPIDataComponentTypeRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIDataComponentTypeRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}

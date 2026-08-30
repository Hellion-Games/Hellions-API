package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.registries.HellionsAPIDataComponentTypeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class DataComponentTypeModuleFabric {
    public static void registerDataComponentTypes(String modid) {
        HellionsAPIDataComponentTypeRegistry module = HellionsAPIDataComponentTypeRegistry.getModule(modid);
        if (module == null) return;
        for (Map.Entry<ResourceLocation, DataComponentType<?>> entry : module.getDataComponentTypeRegistry().entrySet()) {
            Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, entry.getKey(), entry.getValue());
        }
    }
}

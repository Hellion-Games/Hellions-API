package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.FuelRegistry;
import com.helliongames.hellionsapi.registration.holders.ItemDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIItemRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ItemModuleFabric {

    public static void registerItems() {
        for (HellionsAPIItemRegistry module : HellionsAPIItemRegistry.getModules()) {
            for (Map.Entry<ResourceLocation, ItemDataHolder<?>> entry : module.getItemRegistry().entrySet()) {
                // Register item
                Registry.register(BuiltInRegistries.ITEM, entry.getKey(), entry.getValue().get());

                // Register Fuel
                if (entry.getValue().isFuel()) {
                    FuelRegistry.register(entry.getValue().get(), entry.getValue().getFuelDuration());
                }
            }
        }
    }
}

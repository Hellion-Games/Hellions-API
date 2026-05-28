package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.MenuDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIMenuRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class MenuModuleFabric {
    public static void registerMenus(String modid) {
        HellionsAPIMenuRegistry module = HellionsAPIMenuRegistry.getModule(modid);
        if (module == null) return;

        for (Map.Entry<ResourceLocation, MenuDataHolder<?>> entry : module.getMenuRegistry().entrySet()) {
            Registry.register(BuiltInRegistries.MENU, entry.getKey(), entry.getValue().get());
        }
    }
}

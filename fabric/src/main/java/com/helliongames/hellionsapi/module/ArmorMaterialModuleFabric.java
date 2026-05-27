package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.ArmorMaterialDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIArmorMaterialRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ArmorMaterialModuleFabric {
    public static void registerArmorMaterials(String modid) {
        HellionsAPIArmorMaterialRegistry module = HellionsAPIArmorMaterialRegistry.getModule(modid);
        if (module == null) return;

        for (Map.Entry<ResourceLocation, ArmorMaterialDataHolder> entry : module.getArmorMaterialRegistry().entrySet()) {
            Registry.register(BuiltInRegistries.ARMOR_MATERIAL, entry.getKey(), entry.getValue().get());
        }
    }
}

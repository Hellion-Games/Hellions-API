package com.helliongames.hellionsapi;

import com.helliongames.hellionsapi.platform.Services;
import net.minecraft.resources.ResourceLocation;

public class HellionsAPICommon {

    public static void init(String modid) {
        Services.REGISTRY.registerMod(modid);
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(HellionsAPIConstants.MOD_ID, name);
    }
}
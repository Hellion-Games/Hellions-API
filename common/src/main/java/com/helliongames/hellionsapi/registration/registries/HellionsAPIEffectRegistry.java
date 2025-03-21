package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.MobEffectDataHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HellionsAPIEffectRegistry {
    private static final List<HellionsAPIEffectRegistry> MODULES = new ArrayList<>();

    private final String modid;

    public HellionsAPIEffectRegistry(String modid) {
        this.modid = modid;
        MODULES.add(this);
    }

    /** Map of all Effect Resource Locations to their Suppliers. */
    private final Map<ResourceLocation, MobEffectDataHolder<?>> EFFECT_REGISTRY = new HashMap<>();

    public MobEffectDataHolder<?> register(String name, MobEffectDataHolder<?> effect) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.EFFECT_REGISTRY.put(id, effect);
        return effect;
    }

    public Map<ResourceLocation, MobEffectDataHolder<?>> getEffectRegistry() {
        return this.EFFECT_REGISTRY;
    }

    public static List<HellionsAPIEffectRegistry> getModules() {
        return MODULES;
    }
}

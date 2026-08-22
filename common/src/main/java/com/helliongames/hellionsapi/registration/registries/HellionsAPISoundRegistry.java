package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.SoundDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;
import java.util.Map;

public class HellionsAPISoundRegistry {
    private static final Map<String, HellionsAPISoundRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPISoundRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
    }

    /** Map of all Sound Resource Locations to their SoundDataHolders. */
    private final Map<ResourceLocation, SoundDataHolder> SOUND_REGISTRY = new HashMap<>();

    /*
    public static final HellionsAPISoundRegistry SOUND_MODULE = new HellionsAPISoundRegistry("examplemod");

    public static final SoundDataHolder EXAMPLE_SOUND_EVENT = SOUND_MODULE.register("example_sound", SoundDataHolder.of(32.0f));
     */

    public <T extends SoundEvent> SoundDataHolder register(String name, SoundDataHolder soundDataHolder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        soundDataHolder.setResourceLocation(id);

        this.SOUND_REGISTRY.put(id, soundDataHolder);
        return soundDataHolder;
    }

    public Map<ResourceLocation, SoundDataHolder> getSoundRegistry() {
        return this.SOUND_REGISTRY;
    }

    public static Map<String, HellionsAPISoundRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPISoundRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}

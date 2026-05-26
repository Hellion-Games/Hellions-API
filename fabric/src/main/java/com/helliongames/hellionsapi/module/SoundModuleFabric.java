package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.SoundDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPISoundRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.Map;

public class SoundModuleFabric {
    public static void registerSounds(String modid) {
        HellionsAPISoundRegistry module = HellionsAPISoundRegistry.getModule(modid);
        if (module == null) return;
        for (Map.Entry<ResourceLocation, SoundDataHolder> entry : module.getSoundRegistry().entrySet()) {
            // Register sound
            SoundEvent soundEvent = entry.getValue().hasRange() ?
                    SoundEvent.createFixedRangeEvent(entry.getKey(), entry.getValue().getRange()) :
                    SoundEvent.createVariableRangeEvent(entry.getKey());

            Registry.register(BuiltInRegistries.SOUND_EVENT, entry.getKey(), soundEvent);
        }
    }
}

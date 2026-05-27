package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIParticleRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ParticleModuleFabric {
    public static void registerParticles(String modid) {
        HellionsAPIParticleRegistry module = HellionsAPIParticleRegistry.getModule(modid);
        if (module == null) return;
        for (Map.Entry<ResourceLocation, ParticleDataHolder<?>> entry : module.getParticleRegistry().entrySet()) {
            Registry.register(BuiltInRegistries.PARTICLE_TYPE, entry.getKey(), entry.getValue().get());
        }
    }
}

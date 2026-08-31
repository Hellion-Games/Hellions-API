package com.helliongames.hellionsapi.registration.registries.client;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HellionsAPIParticleFactoryRegistry {
    private static final Map<String, HellionsAPIParticleFactoryRegistry> MODULES = new HashMap<>();

    public HellionsAPIParticleFactoryRegistry(String modid) {
        MODULES.put(modid, this);
    }

    /** Map of all ParticleDataHolders to their Factories. */
    private final Map<ParticleDataHolder<?>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> PARTICLE_FACTORY_REGISTRY = new HashMap<>();

    public <T extends ParticleOptions> ParticleDataHolder<T> register(ParticleDataHolder<T> particleDataHolder, Function<SpriteSet, ParticleProvider<T>> factory) {
        this.PARTICLE_FACTORY_REGISTRY.put(particleDataHolder, factory);
        return particleDataHolder;
    }

    public Map<ParticleDataHolder<?>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> getParticleRegistry() {
        return this.PARTICLE_FACTORY_REGISTRY;
    }

    public static Map<String, HellionsAPIParticleFactoryRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIParticleFactoryRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}

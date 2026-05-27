package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class HellionsAPIParticleRegistry {
    private static final Map<String, HellionsAPIParticleRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPIParticleRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
    }

    /** Map of all Particle Resource Locations to their ParticleDataHolders. */
    private final Map<ResourceLocation, ParticleDataHolder<?>> PARTICLE_REGISTRY = new HashMap<>();

    /*
    public static final HellionsAPIParticleRegistry PARTICLE_MODULE = new HellionsAPIParticleRegistry("examplemod");

    public static final ParticleDataHolder<?> EXAMPLE_PARTICLE = PARTICLE_MODULE.register("example_particle", ParticleDataHolder.of(() ->
                    FabricParticleTypes.simple(), EndRodParticle.Provider::new)
    );
    */

    public <T extends ParticleOptions> ParticleDataHolder<T> register(String name, ParticleDataHolder<T> particleDataHolder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.PARTICLE_REGISTRY.put(id, particleDataHolder);
        return particleDataHolder;
    }

    public Map<ResourceLocation, ParticleDataHolder<?>> getParticleRegistry() {
        return this.PARTICLE_REGISTRY;
    }

    public static Map<String, HellionsAPIParticleRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIParticleRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}

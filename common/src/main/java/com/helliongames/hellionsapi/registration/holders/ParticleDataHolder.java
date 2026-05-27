package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Function;
import java.util.function.Supplier;

public class ParticleDataHolder<T extends ParticleOptions> {
    private ParticleType<T> cachedEntry;
    private final Supplier<ParticleType<T>> entrySupplier;
    private final Function<SpriteSet, ParticleProvider<T>> factory;

    public ParticleDataHolder(Supplier<ParticleType<T>> entrySupplier, Function<SpriteSet, ParticleProvider<T>>  factory) {
        this.entrySupplier = entrySupplier;
        this.factory = factory;
    }

    public static <I extends ParticleOptions> ParticleDataHolder<I> of(Supplier<ParticleType<I>> particleSupplier, Function<SpriteSet, ParticleProvider<I>>  particleFactory) {
        return new ParticleDataHolder<>(particleSupplier, particleFactory);
    }

    public Function<SpriteSet, ParticleProvider<T>> getFactory() {
        return factory;
    }

    /**
     * Retrieves the cached entry if it exists, otherwise calls the supplier to create a new entry.
     * @return The cached entry, or a new entry if the cached entry does not exist.
     */
    public ParticleType<T> get() {
        if (this.cachedEntry != null) return cachedEntry;

        ParticleType<T> entry = entrySupplier.get();
        this.cachedEntry = entry;

        return entry;
    }
}

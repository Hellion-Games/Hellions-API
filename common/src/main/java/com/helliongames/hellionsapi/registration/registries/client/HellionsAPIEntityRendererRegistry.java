package com.helliongames.hellionsapi.registration.registries.client;

import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class HellionsAPIEntityRendererRegistry {
    private static final Map<String, HellionsAPIEntityRendererRegistry> MODULES = new HashMap<>();

    public HellionsAPIEntityRendererRegistry(String modid) {
        MODULES.put(modid, this);
    }

    /** Map of all EntityTypes to their EntityRendererProviders. */
    private final List<RendererEntry<? extends Entity, ? extends Entity>> ENTITY_RENDERER_REGISTRY = new ArrayList<>();

    public <E extends Entity, T extends E> void register(EntityTypeDataHolder<T> entityTypeDataHolder, EntityRendererProvider<E> rendererProvider) {
        this.ENTITY_RENDERER_REGISTRY.add(new RendererEntry<>(entityTypeDataHolder, rendererProvider));
    }

    public static Map<String, HellionsAPIEntityRendererRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIEntityRendererRegistry getModule(String modid) {
        return MODULES.get(modid);
    }

    public <E extends Entity, T extends E> void forEachEntry(Consumer<RendererEntry<E, T>> consumer) {
        for (RendererEntry<? extends Entity, ? extends Entity> entry : this.ENTITY_RENDERER_REGISTRY) {
            // Capture the wildcard as E, T
            @SuppressWarnings("unchecked")
            RendererEntry<E, T> casted = (RendererEntry<E, T>) entry;
            consumer.accept(casted);
        }
    }

    public static final class RendererEntry<E extends Entity, T extends E> {
        final EntityTypeDataHolder<T> holder;
        final EntityRendererProvider<E> provider;

        RendererEntry(EntityTypeDataHolder<T> holder, EntityRendererProvider<E> provider) {
            this.holder = holder;
            this.provider = provider;
        }

        public EntityTypeDataHolder<T> getHolder() {
            return holder;
        }

        public EntityRendererProvider<E> getProvider() {
            return provider;
        }
    }
}
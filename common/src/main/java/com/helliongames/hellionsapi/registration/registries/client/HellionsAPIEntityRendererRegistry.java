package com.helliongames.hellionsapi.registration.registries.client;

import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HellionsAPIEntityRendererRegistry {
    /** Map of all EntityTypes to their EntityRendererProviders. */
    private static final List<RendererEntry<? extends Entity, ? extends Entity>> ENTITY_RENDERER_REGISTRY = new ArrayList<>();

    /**
    static {
        HellionsAPIEntityRendererHolder.register(HellionsAPIEntityTypeModule.EXAMPLE, ExampleEntityRenderer::new);
    }
    **/

    public static <E extends Entity, T extends E> void register(EntityTypeDataHolder<T> entityTypeDataHolder, EntityRendererProvider<E> rendererProvider) {
        ENTITY_RENDERER_REGISTRY.add(new RendererEntry<>(entityTypeDataHolder, rendererProvider));
    }

    public static <E extends Entity, T extends E> void forEachEntry(Consumer<RendererEntry<E, T>> consumer) {

        for (RendererEntry<? extends Entity, ? extends Entity> entry : ENTITY_RENDERER_REGISTRY) {
            // Capture the wildcard as E, T
            @SuppressWarnings("unchecked")
            RendererEntry<E, T> casted = (RendererEntry<E, T>) entry;
            consumer.accept(casted);
        }
    }


    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {
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
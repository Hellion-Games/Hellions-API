package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import java.util.HashMap;
import java.util.Map;

public class HellionsAPIEntityRendererRegistry {
    /** Map of all EntityTypes to their EntityRendererProviders. */
    private static final Map<EntityTypeDataHolder, EntityRendererProvider> ENTITY_RENDERER_REGISTRY = new HashMap<>();

    /**
    static {
        HellionsAPIEntityRendererHolder.register(HellionsAPIEntityTypeModule.EXAMPLE, ExampleEntityRenderer::new);
    }
    **/

    public static void register(EntityTypeDataHolder entityTypeDataHolder, EntityRendererProvider rendererProvider) {
        ENTITY_RENDERER_REGISTRY.put(entityTypeDataHolder, rendererProvider);
    }

    public static Map<EntityTypeDataHolder, EntityRendererProvider> getEntityRendererRegistry() {
        return ENTITY_RENDERER_REGISTRY;
    }

    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {
    }
}
package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import java.util.Map;

public class HellionsAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HellionsAPICommonClient.init();
        registerEntityRenderers();
    }

    private void registerEntityRenderers() {
        for (Map.Entry<EntityTypeDataHolder, EntityRendererProvider> entry : HellionsAPIEntityRendererRegistry.getEntityRendererRegistry().entrySet()) {
            // Register entity renderers
            EntityRendererRegistry.register(entry.getKey().get(), entry.getValue());
        }
    }
}

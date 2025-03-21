package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRendererRegistry;
import com.helliongames.hellionsapi.registration.EntityTypeDataHolder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.util.Map;

public class HellionsAPINeoForgeClient {
    public static void init(IEventBus modEventBus) {
        HellionsAPICommonClient.init();

        modEventBus.addListener(HellionsAPINeoForgeClient::clientSetup);
        modEventBus.addListener(HellionsAPINeoForgeClient::registerEntityRenderers);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (Map.Entry<EntityTypeDataHolder, EntityRendererProvider> entry : HellionsAPIEntityRendererRegistry.getEntityRendererRegistry().entrySet()) {
            // Register entity renderers
            event.registerEntityRenderer(entry.getKey().get(), entry.getValue());
        }
    }
}

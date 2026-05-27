package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRendererRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIParticleRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class HellionsAPINeoForgeClient {
    public static void init(IEventBus modEventBus) {
        HellionsAPICommonClient.init();

        modEventBus.addListener(HellionsAPINeoForgeClient::clientSetup);
        modEventBus.addListener(HellionsAPINeoForgeClient::registerEntityRenderers);
        modEventBus.addListener(HellionsAPINeoForgeClient::registerParticleFactories);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        HellionsAPIEntityRendererRegistry.forEachEntry(entry -> event.registerEntityRenderer(entry.getHolder().get(), entry.getProvider()));
    }

    private static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        for (HellionsAPIParticleRegistry module : HellionsAPIParticleRegistry.getModules().values()) {
            for (ParticleDataHolder<?> holder : module.getParticleRegistry().values()) {
                registerParticleFactory(event, holder);
            }
        }
    }

    private static <T extends ParticleOptions> void registerParticleFactory(RegisterParticleProvidersEvent event, ParticleDataHolder<T> holder) {
        event.registerSpriteSet(holder.get(), sprites -> holder.getFactory().apply(sprites));
    }
}

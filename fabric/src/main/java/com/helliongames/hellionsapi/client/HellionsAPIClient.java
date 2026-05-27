package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRendererRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIParticleRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.core.particles.ParticleOptions;

public class HellionsAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HellionsAPICommonClient.init();
        registerEntityRenderers();
        registerParticleFactories();
    }

    private void registerEntityRenderers() {
        HellionsAPIEntityRendererRegistry.forEachEntry(entry -> EntityRendererRegistry.register(entry.getHolder().get(), entry.getProvider()));
    }

    private void registerParticleFactories() {
        for (HellionsAPIParticleRegistry module : HellionsAPIParticleRegistry.getModules().values()) {
            for (ParticleDataHolder<?> holder : module.getParticleRegistry().values()) {
                registerParticleFactory(holder);
            }
        }
    }

    private <T extends ParticleOptions> void registerParticleFactory(ParticleDataHolder<T> holder) {
        ParticleFactoryRegistry.getInstance().register(holder.get(), sprites -> holder.getFactory().apply(sprites));
    }
}

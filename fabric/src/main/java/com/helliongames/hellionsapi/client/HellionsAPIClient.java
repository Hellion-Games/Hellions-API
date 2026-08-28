package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIParticleRegistry;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIEntityRendererRegistry;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIMenuScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class HellionsAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HellionsAPICommonClient.init();
        registerEntityRenderers();
        registerParticleFactories();
        registerMenuScreens();
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

    private void registerMenuScreens() {
        HellionsAPIMenuScreenRegistry.forEachEntry(this::registerMenuScreen);
    }

    private <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> void registerMenuScreen(HellionsAPIMenuScreenRegistry.ScreenEntry<M, S> entry) {
        MenuScreens.register(entry.getHolder().get(), entry.getScreenConstructor()::create);
    }
}

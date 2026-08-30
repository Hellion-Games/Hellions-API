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
    }

    public static void init(String modid) {
        registerEntityRenderers(modid);
        registerParticleFactories(modid);
        registerMenuScreens(modid);
    }

    private static void registerEntityRenderers(String modid) {
        HellionsAPIEntityRendererRegistry.getModule(modid).forEachEntry(entry -> EntityRendererRegistry.register(entry.getHolder().get(), entry.getProvider()));
    }

    private static void registerParticleFactories(String modid) {
        HellionsAPIParticleRegistry module = HellionsAPIParticleRegistry.getModule(modid);
        for (ParticleDataHolder<?> holder : module.getParticleRegistry().values()) {
            registerParticleFactory(holder);
        }
    }

    private static <T extends ParticleOptions> void registerParticleFactory(ParticleDataHolder<T> holder) {
        ParticleFactoryRegistry.getInstance().register(holder.get(), sprites -> holder.getFactory().apply(sprites));
    }

    private static void registerMenuScreens(String modid) {
        HellionsAPIMenuScreenRegistry.getModule(modid).forEachEntry(HellionsAPIClient::registerMenuScreen);
    }

    private static <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> void registerMenuScreen(HellionsAPIMenuScreenRegistry.ScreenEntry<M, S> entry) {
        MenuScreens.register(entry.getHolder().get(), entry.getScreenConstructor()::create);
    }
}

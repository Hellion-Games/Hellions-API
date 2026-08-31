package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIEntityRendererRegistry;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIMenuScreenRegistry;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIParticleFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.Map;
import java.util.function.Function;

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
        HellionsAPIEntityRendererRegistry module = HellionsAPIEntityRendererRegistry.getModule(modid);
        if (module == null) return;
        module.forEachEntry(entry -> EntityRendererRegistry.register(entry.getHolder().get(), entry.getProvider()));
    }

    private static void registerParticleFactories(String modid) {
        HellionsAPIParticleFactoryRegistry module = HellionsAPIParticleFactoryRegistry.getModule(modid);
        if (module == null) return;
        for (Map.Entry<ParticleDataHolder<?>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> entry : module.getParticleRegistry().entrySet()) {
            registerParticleFactory(entry);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends ParticleOptions> void registerParticleFactory(Map.Entry<ParticleDataHolder<?>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> entry) {
        ParticleDataHolder<T> holder = (ParticleDataHolder<T>) entry.getKey();
        Function<SpriteSet, ParticleProvider<T>> factory = (Function<SpriteSet, ParticleProvider<T>>) entry.getValue();

        ParticleFactoryRegistry.getInstance().register(holder.get(), factory::apply);
    }

    private static void registerMenuScreens(String modid) {
        HellionsAPIMenuScreenRegistry module = HellionsAPIMenuScreenRegistry.getModule(modid);
        if (module == null) return;
        module.forEachEntry(HellionsAPIClient::registerMenuScreen);
    }

    private static <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> void registerMenuScreen(HellionsAPIMenuScreenRegistry.ScreenEntry<M, S> entry) {
        MenuScreens.register(entry.getHolder().get(), entry.getScreenConstructor()::create);
    }
}

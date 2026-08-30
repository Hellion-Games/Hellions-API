package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIParticleRegistry;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIEntityRendererRegistry;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIMenuScreenRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class HellionsAPINeoForgeClient {
    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(HellionsAPINeoForgeClient::clientSetup);
        modEventBus.addListener(HellionsAPINeoForgeClient::registerEntityRenderers);
        modEventBus.addListener(HellionsAPINeoForgeClient::registerParticleFactories);
        modEventBus.addListener(HellionsAPINeoForgeClient::registerMenuScreens);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (HellionsAPIEntityRendererRegistry module : HellionsAPIEntityRendererRegistry.getModules().values()) {
            module.forEachEntry(entry -> event.registerEntityRenderer(entry.getHolder().get(), entry.getProvider()));
        }
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

    private static void registerMenuScreens(RegisterMenuScreensEvent event) {
        for (HellionsAPIMenuScreenRegistry module : HellionsAPIMenuScreenRegistry.getModules().values()) {
            module.forEachEntry(entry -> registerMenuScreen(event, entry));
        }
    }

    private static <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> void registerMenuScreen(RegisterMenuScreensEvent event, HellionsAPIMenuScreenRegistry.ScreenEntry<M, S> entry) {
        @SuppressWarnings("unchecked")
        MenuScreens.ScreenConstructor<M, S> constructor = (MenuScreens.ScreenConstructor<M, S>) entry.getScreenConstructor();
        event.register(entry.getHolder().get(), constructor);
    }
}

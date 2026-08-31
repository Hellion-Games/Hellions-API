package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIEntityRendererRegistry;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIMenuScreenRegistry;
import com.helliongames.hellionsapi.registration.registries.client.HellionsAPIParticleFactoryRegistry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.Map;
import java.util.function.Function;

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
        for (HellionsAPIParticleFactoryRegistry module : HellionsAPIParticleFactoryRegistry.getModules().values()) {
            for (Map.Entry<ParticleDataHolder<?>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> entry : module.getParticleRegistry().entrySet()) {
                registerParticleFactory(event, entry);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends ParticleOptions> void registerParticleFactory(RegisterParticleProvidersEvent event, Map.Entry<ParticleDataHolder<?>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> entry) {
        ParticleDataHolder<T> holder = (ParticleDataHolder<T>) entry.getKey();
        Function<SpriteSet, ParticleProvider<T>> factory = (Function<SpriteSet, ParticleProvider<T>>) entry.getValue();

        event.registerSpriteSet(holder.get(), factory::apply);
    }

    private static void registerMenuScreens(RegisterMenuScreensEvent event) {
        for (HellionsAPIMenuScreenRegistry module : HellionsAPIMenuScreenRegistry.getModules().values()) {
            module.forEachEntry(entry -> registerMenuScreen(event, entry));
        }
    }

    private static <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> void registerMenuScreen(RegisterMenuScreensEvent event, HellionsAPIMenuScreenRegistry.ScreenEntry<M, S> entry) {
        event.register(entry.getHolder().get(), entry.getScreenConstructor()::create);
    }
}

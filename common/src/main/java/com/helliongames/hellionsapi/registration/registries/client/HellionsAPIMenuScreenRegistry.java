package com.helliongames.hellionsapi.registration.registries.client;

import com.helliongames.hellionsapi.registration.holders.MenuDataHolder;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class HellionsAPIMenuScreenRegistry {
    private static final Map<String, HellionsAPIMenuScreenRegistry> MODULES = new HashMap<>();

    public HellionsAPIMenuScreenRegistry(String modid) {
        MODULES.put(modid, this);
    }

    private final List<ScreenEntry<?, ?>> SCREEN_REGISTRY = new ArrayList<>();

    public <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> void register(MenuDataHolder<M> holder, ScreenConstructor<M, S> screenConstructor) {
        this.SCREEN_REGISTRY.add(new ScreenEntry<>(holder, screenConstructor));
    }

    public <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> void forEachEntry(Consumer<ScreenEntry<M, S>> consumer) {
        for (ScreenEntry<?, ?> entry : this.SCREEN_REGISTRY) {
            @SuppressWarnings("unchecked")
            ScreenEntry<M, S> casted = (ScreenEntry<M, S>) entry;
            consumer.accept(casted);
        }
    }

    public static Map<String, HellionsAPIMenuScreenRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIMenuScreenRegistry getModule(String modid) {
        return MODULES.get(modid);
    }

    public static final class ScreenEntry<M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> {
        private final MenuDataHolder<M> holder;
        private final ScreenConstructor<M, S> screenConstructor;

        ScreenEntry(MenuDataHolder<M> holder, ScreenConstructor<M, S> screenConstructor) {
            this.holder = holder;
            this.screenConstructor = screenConstructor;
        }

        public MenuDataHolder<M> getHolder() { return holder; }
        public ScreenConstructor<M, S> getScreenConstructor() { return screenConstructor; }
    }

    @FunctionalInterface
    public interface ScreenConstructor<M extends AbstractContainerMenu, S extends AbstractContainerScreen<M> & MenuAccess<M>> {
        S create(M menu, Inventory inventory, Component title);
    }
}
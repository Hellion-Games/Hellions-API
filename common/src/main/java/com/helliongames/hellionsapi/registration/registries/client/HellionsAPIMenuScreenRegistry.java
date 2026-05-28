package com.helliongames.hellionsapi.registration.registries.client;

import com.helliongames.hellionsapi.registration.holders.MenuDataHolder;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HellionsAPIMenuScreenRegistry {
    private static final List<ScreenEntry<?, ?>> SCREEN_REGISTRY = new ArrayList<>();

    public static <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M>> void register(
            MenuDataHolder<M> holder,
            ScreenConstructor<M, S> screenConstructor) {
        SCREEN_REGISTRY.add(new ScreenEntry<>(holder, screenConstructor));
    }

    public static <M extends AbstractContainerMenu, S extends AbstractContainerScreen<M>> void forEachEntry(
            Consumer<ScreenEntry<M, S>> consumer) {
        for (ScreenEntry<?, ?> entry : SCREEN_REGISTRY) {
            @SuppressWarnings("unchecked")
            ScreenEntry<M, S> casted = (ScreenEntry<M, S>) entry;
            consumer.accept(casted);
        }
    }

    public static final class ScreenEntry<M extends AbstractContainerMenu, S extends AbstractContainerScreen<M>> {
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
    public interface ScreenConstructor<M extends AbstractContainerMenu, S extends AbstractContainerScreen<M>> {
        S create(M menu, Inventory inventory, Component title);
    }
}
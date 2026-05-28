package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.function.Supplier;

public class MenuDataHolder<T extends AbstractContainerMenu> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDataHolder.class);

    private MenuType<T> cachedEntry;
    private final Supplier<MenuType<T>> entrySupplier;

    public MenuDataHolder(Supplier<MenuType<T>> entrySupplier) {
        this.entrySupplier = entrySupplier;
    }

    public static <I extends AbstractContainerMenu> MenuDataHolder<I> of(Supplier<MenuType<I>> entrySupplier) {
        return new MenuDataHolder<>(entrySupplier);
    }

    public static <I extends AbstractContainerMenu> MenuDataHolder<I> of(MenuFactory<I> menuSupplier, FeatureFlagSet requiredFeatures) {
        return new MenuDataHolder<>(() -> {
            try {
                Class<?> menuSupplierClass = Arrays.stream(MenuType.class.getDeclaredClasses())
                        .filter(Class::isInterface)
                        .findFirst()
                        .orElseThrow();

                Constructor<?> constructor = MenuType.class.getDeclaredConstructor(menuSupplierClass, FeatureFlagSet.class);
                constructor.setAccessible(true);

                Object supplierInstance = Proxy.newProxyInstance(
                        menuSupplierClass.getClassLoader(),
                        new Class<?>[]{ menuSupplierClass },
                        (proxy, method, args) -> menuSupplier.create((int) args[0], (Inventory) args[1])
                );

                @SuppressWarnings("unchecked")
                MenuType<I> menuType = (MenuType<I>) constructor.newInstance(supplierInstance, requiredFeatures);
                return menuType;
            } catch (Exception e) {
                LOGGER.error("Failed to create MenuType via reflection. Ensure the menu constructor matches (int, Inventory).", e);
                return null;
            }
        });
    }

    public static <I extends AbstractContainerMenu> MenuDataHolder<I> of(MenuFactory<I> menuSupplier) {
        return of(menuSupplier, FeatureFlags.VANILLA_SET);
    }

    @FunctionalInterface
    public interface MenuFactory<T extends AbstractContainerMenu> {
        T create(int containerId, Inventory inventory);
    }

    public MenuType<T> get() {
        if (this.cachedEntry != null) return cachedEntry;
        MenuType<T> entry = entrySupplier.get();
        this.cachedEntry = entry;
        return entry;
    }
}
package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.MenuDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.HashMap;
import java.util.Map;

public class HellionsAPIMenuRegistry {
    private static final Map<String, HellionsAPIMenuRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPIMenuRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
    }

    /** Map of all Menu Resource Locations to their MenuDataHolders. */
    private final Map<ResourceLocation, MenuDataHolder<?>> MENU_REGISTRY = new HashMap<>();

    /*
    public static final HellionsAPIMenuRegistry MENU_MODULE = new HellionsAPIMenuRegistry("examplemod");

    public static final MenuDataHolder<ChestMenu> EXAMPLE_MENU = MENU_MODULE.register(
            "example_menu",
            MenuDataHolder.of(ExampleMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );

    ExampleMenu must have a constructor that takes an int and an Inventory to work in this implementation.
    */

    public <T extends AbstractContainerMenu> MenuDataHolder<T> register(String name, MenuDataHolder<T> holder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.MENU_REGISTRY.put(id, holder);
        return holder;
    }

    public Map<ResourceLocation, MenuDataHolder<?>> getMenuRegistry() {
        return this.MENU_REGISTRY;
    }

    public static Map<String, HellionsAPIMenuRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIMenuRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}
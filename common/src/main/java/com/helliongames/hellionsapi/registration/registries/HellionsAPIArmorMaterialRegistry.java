package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.ArmorMaterialDataHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class HellionsAPIArmorMaterialRegistry {
    private static final Map<String, HellionsAPIArmorMaterialRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPIArmorMaterialRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
    }

    /** Map of all ArmorMaterial Resource Locations to their ArmorMaterialDataHolders. */
    private final Map<ResourceLocation, ArmorMaterialDataHolder> ARMOR_MATERIAL_REGISTRY = new HashMap<>();

    /*
    public static final HellionsAPIArmorMaterialRegistry ARMOR_MATERIAL_MODULE = new HellionsAPIArmorMaterialRegistry("examplemod");

    public static final ArmorMaterialDataHolder EXAMPLE_ARMOR_MATERIAL = ARMOR_MATERIAL_MODULE.register(
        "example_armor_material",
        ArmorMaterialDataHolder.of(() -> new ArmorMaterial(
            Map.of(
                ArmorItem.Type.HELMET, 3,
                ArmorItem.Type.CHESTPLATE, 8,
                ArmorItem.Type.LEGGINGS, 6,
                ArmorItem.Type.BOOTS, 3
            ),
            15,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.IRON_INGOT),
            List.of(
                new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("examplemod", "example_armor_material"))
            ),
            0.0F,
            0.0F
        ))
    );
    */

    public ArmorMaterialDataHolder register(String name, ArmorMaterialDataHolder holder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.ARMOR_MATERIAL_REGISTRY.put(id, holder);
        return holder;
    }

    public Map<ResourceLocation, ArmorMaterialDataHolder> getArmorMaterialRegistry() {
        return this.ARMOR_MATERIAL_REGISTRY;
    }

    public static Map<String, HellionsAPIArmorMaterialRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIArmorMaterialRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}
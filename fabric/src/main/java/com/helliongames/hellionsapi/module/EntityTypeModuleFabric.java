package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.Map;

public class EntityTypeModuleFabric {

    public static void registerEntities(String modid) {
        HellionsAPIEntityRegistry module = HellionsAPIEntityRegistry.getModule(modid);
        for (Map.Entry<ResourceLocation, EntityTypeDataHolder<? extends Entity>> entry : module.getEntityTypeRegistry().entrySet()) {
            // Register entity type
            Registry.register(BuiltInRegistries.ENTITY_TYPE, entry.getKey(), entry.getValue().get());

            // Register entity attributes, if present
            if (entry.getValue().hasAttributes()) {
                AttributeSupplier.Builder attributesBuilder = entry.getValue().getAttributesSupplier().get();
                FabricDefaultAttributeRegistry.register((EntityType<? extends LivingEntity>) entry.getValue().get(), attributesBuilder);
            }
        }
    }
}

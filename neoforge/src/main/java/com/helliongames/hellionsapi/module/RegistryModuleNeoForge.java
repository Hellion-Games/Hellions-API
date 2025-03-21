package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.BlockDataHolder;
import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import com.helliongames.hellionsapi.registration.holders.ItemDataHolder;
import com.helliongames.hellionsapi.registration.holders.MobEffectDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIBlockRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEffectRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;

@EventBusSubscriber(modid = "hellionsapi", bus = EventBusSubscriber.Bus.MOD)
public class RegistryModuleNeoForge {

    @SubscribeEvent
    public static void registerValues(RegisterEvent event) {
        if (event.getRegistry().equals(BuiltInRegistries.ENTITY_TYPE)) {
            for (HellionsAPIEntityRegistry module : HellionsAPIEntityRegistry.getModules()) {
                for (Map.Entry<ResourceLocation, EntityTypeDataHolder> entry : module.getEntityTypeRegistry().entrySet()) {
                    // Register entity type
                    event.register(Registries.ENTITY_TYPE, entityTypeRegisterHelper ->
                            entityTypeRegisterHelper.register(entry.getKey(), entry.getValue().get())
                    );
                }
            }
        } else if (event.getRegistry().equals(BuiltInRegistries.ITEM)) {
            for (HellionsAPIItemRegistry module : HellionsAPIItemRegistry.getModules()) {
                for (Map.Entry<ResourceLocation, ItemDataHolder<?>> entry : module.getItemRegistry().entrySet()) {
                    // Register item
                    event.register(Registries.ITEM, itemRegistryHelper ->
                            itemRegistryHelper.register(entry.getKey(), entry.getValue().get())
                    );
                }
            }
        } else if (event.getRegistry().equals(BuiltInRegistries.BLOCK)) {
            for (HellionsAPIBlockRegistry module : HellionsAPIBlockRegistry.getModules()) {
                for (Map.Entry<ResourceLocation, BlockDataHolder<?>> entry : module.getBlockRegistry().entrySet()) {
                    // Register block
                    event.register(Registries.BLOCK, blockRegistryHelper ->
                            blockRegistryHelper.register(entry.getKey(), entry.getValue().get())
                    );

                    // Register the block items
                    if (entry.getValue().hasItem()) {
                        event.register(Registries.ITEM, itemRegistryHelper ->
                                itemRegistryHelper.register(entry.getKey(), entry.getValue().getBlockItem().get())
                        );
                    }
                }
            }
        } else if (event.getRegistry().equals(BuiltInRegistries.MOB_EFFECT)) {
            for (HellionsAPIEffectRegistry module : HellionsAPIEffectRegistry.getModules()) {
                for (Map.Entry<ResourceLocation, MobEffectDataHolder<?>> entry : module.getEffectRegistry().entrySet()) {
                    // Register effect
                    event.register(Registries.MOB_EFFECT, entry.getKey(), entry.getValue()::get);

                    if (entry.getValue().hasPotion()) {
                        String id = entry.getKey().getPath();

                        event.register(Registries.POTION, entry.getKey(), () ->
                                new Potion(new MobEffectInstance(entry.getValue().get(), 3600)));
                        event.register(Registries.POTION, IECommon.id("long_" + id), () ->
                                new Potion(id, new MobEffectInstance(entry.getValue().get(), 9600)));
                        event.register(Registries.POTION, IECommon.id("strong_" + id), () ->
                                new Potion(id, new MobEffectInstance(entry.getValue().get(), 1800, 1)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        for (HellionsAPIEntityRegistry module : HellionsAPIEntityRegistry.getModules()) {
            for (Map.Entry<ResourceLocation, EntityTypeDataHolder> entry : module.getEntityTypeRegistry().entrySet()) {
                // Register entity attributes

                AttributeSupplier.Builder builder = (AttributeSupplier.Builder) entry.getValue().getAttributesSupplier().get();
                // Attach required Forge attributes and register
                builder.add(NeoForgeMod.SWIM_SPEED)
                        .add(NeoForgeMod.NAMETAG_DISTANCE);

                event.put(entry.getValue().get(), builder.build());
            }
        }
    }
}

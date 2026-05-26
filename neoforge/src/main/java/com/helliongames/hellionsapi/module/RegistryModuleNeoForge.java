package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.*;
import com.helliongames.hellionsapi.registration.registries.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
            for (Map.Entry<String, HellionsAPIEntityRegistry> module : HellionsAPIEntityRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, EntityTypeDataHolder<? extends Entity>> entry : module.getValue().getEntityTypeRegistry().entrySet()) {
                    // Register entity type
                    event.register(Registries.ENTITY_TYPE, entityTypeRegisterHelper ->
                            entityTypeRegisterHelper.register(entry.getKey(), entry.getValue().get())
                    );
                }
            }
        } else if (event.getRegistry().equals(BuiltInRegistries.ITEM)) {
            for (Map.Entry<String, HellionsAPIItemRegistry> module : HellionsAPIItemRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, ItemDataHolder<?>> entry : module.getValue().getItemRegistry().entrySet()) {
                    // Register item
                    event.register(Registries.ITEM, itemRegistryHelper ->
                            itemRegistryHelper.register(entry.getKey(), entry.getValue().get())
                    );
                }
            }
        } else if (event.getRegistry().equals(BuiltInRegistries.BLOCK)) {
            for (Map.Entry<String, HellionsAPIBlockRegistry> module : HellionsAPIBlockRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, BlockDataHolder<?>> entry : module.getValue().getBlockRegistry().entrySet()) {
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
            for (Map.Entry<String, HellionsAPIEffectRegistry> module : HellionsAPIEffectRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, MobEffectDataHolder<?>> entry : module.getValue().getEffectRegistry().entrySet()) {
                    // Register effect
                    Registry.register(BuiltInRegistries.MOB_EFFECT, entry.getKey(), entry.getValue().get());

                    if (entry.getValue().hasPotion()) {
                        String id = entry.getKey().getPath();
                        String namespace = entry.getKey().getNamespace();

                        event.register(Registries.POTION, entry.getKey(),
                                () -> new Potion(new MobEffectInstance(Holder.direct(entry.getValue().get()), 3600)));
                        event.register(Registries.POTION, ResourceLocation.fromNamespaceAndPath(namespace, "long_" + id),
                                () -> new Potion(id, new MobEffectInstance(Holder.direct(entry.getValue().get()), 9600)));
                        event.register(Registries.POTION, ResourceLocation.fromNamespaceAndPath(namespace, "strong_" + id),
                                () -> new Potion(id, new MobEffectInstance(Holder.direct(entry.getValue().get()), 1800, 1)));
                    }
                }
            }
        } else if (event.getRegistry().equals(BuiltInRegistries.SOUND_EVENT)) {
            for (Map.Entry<String, HellionsAPISoundRegistry> module : HellionsAPISoundRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, SoundDataHolder> entry : module.getValue().getSoundRegistry().entrySet()) {
                    // Register sound
                    SoundEvent soundEvent = entry.getValue().hasRange() ?
                            SoundEvent.createFixedRangeEvent(entry.getKey(), entry.getValue().getRange()) :
                            SoundEvent.createVariableRangeEvent(entry.getKey());

                    Registry.register(BuiltInRegistries.SOUND_EVENT, entry.getKey(), soundEvent);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        for (Map.Entry<String, HellionsAPIEntityRegistry> module : HellionsAPIEntityRegistry.getModules().entrySet()) {
            for (Map.Entry<ResourceLocation, EntityTypeDataHolder<? extends Entity>> entry : module.getValue().getEntityTypeRegistry().entrySet()) {
                if (!entry.getValue().hasAttributes()) continue;

                // Register entity attributes
                AttributeSupplier.Builder builder = entry.getValue().getAttributesSupplier().get();
                // Attach required Forge attributes and register
                builder.add(NeoForgeMod.SWIM_SPEED)
                        .add(NeoForgeMod.NAMETAG_DISTANCE);

                event.put((EntityType<? extends LivingEntity>) entry.getValue().get(), builder.build());
            }
        }
    }
}

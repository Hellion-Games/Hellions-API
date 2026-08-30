package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.ArmorMaterialDataHolder;
import com.helliongames.hellionsapi.registration.holders.BlockDataHolder;
import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import com.helliongames.hellionsapi.registration.holders.ItemDataHolder;
import com.helliongames.hellionsapi.registration.holders.MenuDataHolder;
import com.helliongames.hellionsapi.registration.holders.MobEffectDataHolder;
import com.helliongames.hellionsapi.registration.holders.ParticleDataHolder;
import com.helliongames.hellionsapi.registration.holders.SoundDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIArmorMaterialRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIBlockRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIDataComponentTypeRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEffectRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIItemRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIMenuRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIParticleRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPISoundRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;

@EventBusSubscriber(modid = "hellionsapi", bus = EventBusSubscriber.Bus.MOD)
public class RegistryModuleNeoForge {

    @SubscribeEvent
    public static void registerValues(RegisterEvent event) {
        if (event.getRegistry().equals(Registries.ENTITY_TYPE)) {
            for (Map.Entry<String, HellionsAPIEntityRegistry> module : HellionsAPIEntityRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, EntityTypeDataHolder<? extends Entity>> entry : module.getValue().getEntityTypeRegistry().entrySet()) {
                    // Register entity type
                    event.register(Registries.ENTITY_TYPE, entityTypeRegisterHelper ->
                            entityTypeRegisterHelper.register(entry.getKey(), entry.getValue().get())
                    );
                }
            }
        } else if (event.getRegistry().equals(Registries.ITEM)) {
            for (Map.Entry<String, HellionsAPIItemRegistry> module : HellionsAPIItemRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, ItemDataHolder<?>> entry : module.getValue().getItemRegistry().entrySet()) {
                    // Register item
                    event.register(Registries.ITEM, itemRegistryHelper ->
                            itemRegistryHelper.register(entry.getKey(), entry.getValue().get())
                    );
                }
            }
        } else if (event.getRegistry().equals(Registries.BLOCK)) {
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
        } else if (event.getRegistry().equals(Registries.MOB_EFFECT)) {
            for (Map.Entry<String, HellionsAPIEffectRegistry> module : HellionsAPIEffectRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, MobEffectDataHolder<?>> entry : module.getValue().getEffectRegistry().entrySet()) {
                    // Register effect
                    event.register(Registries.MOB_EFFECT, helper -> helper.register(entry.getKey(), entry.getValue().get()));

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
        } else if (event.getRegistry().equals(Registries.SOUND_EVENT)) {
            for (Map.Entry<String, HellionsAPISoundRegistry> module : HellionsAPISoundRegistry.getModules().entrySet()) {
                for (Map.Entry<ResourceLocation, SoundDataHolder> entry : module.getValue().getSoundRegistry().entrySet()) {
                    // Register sound
                    event.register(Registries.SOUND_EVENT, helper -> helper.register(entry.getKey(), entry.getValue().get()));
                }
            }
        } else if (event.getRegistry().equals(Registries.PARTICLE_TYPE)) {
            event.register(Registries.PARTICLE_TYPE, helper -> {
                for (HellionsAPIParticleRegistry module : HellionsAPIParticleRegistry.getModules().values()) {
                    for (Map.Entry<ResourceLocation, ParticleDataHolder<?>> entry : module.getParticleRegistry().entrySet()) {
                        helper.register(entry.getKey(), entry.getValue().get());
                    }
                }
            });
        } else if (event.getRegistry().equals(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS)) {
            event.register(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, helper ->
                    helper.register(
                            ResourceLocation.fromNamespaceAndPath("hellionsapi", "hellions_loot_modifier"),
                            HellionsLootModifier.CODEC
                    )
            );
        } else if (event.getRegistry().equals(Registries.ARMOR_MATERIAL)) {
            event.register(Registries.ARMOR_MATERIAL, helper -> {
                for (HellionsAPIArmorMaterialRegistry module : HellionsAPIArmorMaterialRegistry.getModules().values()) {
                    for (Map.Entry<ResourceLocation, ArmorMaterialDataHolder> entry : module.getArmorMaterialRegistry().entrySet()) {
                        helper.register(entry.getKey(), entry.getValue().get());
                    }
                }
            });
        } else if (event.getRegistry().equals(Registries.MENU)) {
            event.register(Registries.MENU, helper -> {
                for (HellionsAPIMenuRegistry module : HellionsAPIMenuRegistry.getModules().values()) {
                    for (Map.Entry<ResourceLocation, MenuDataHolder<?>> entry : module.getMenuRegistry().entrySet()) {
                        helper.register(entry.getKey(), entry.getValue().get());
                    }
                }
            });
        } else if (event.getRegistry().equals(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS)) {
            event.register(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, helper ->
                    helper.register(
                            ResourceLocation.fromNamespaceAndPath("hellionsapi", "hellions_biome_modifier"),
                            HellionsBiomeModifier.CODEC
                    )
            );
        } else if (event.getRegistry().equals(BuiltInRegistries.DATA_COMPONENT_TYPE)) {
            event.register(Registries.DATA_COMPONENT_TYPE, helper -> {
                for (HellionsAPIDataComponentTypeRegistry module : HellionsAPIDataComponentTypeRegistry.getModules().values()) {
                    for (Map.Entry<ResourceLocation, DataComponentType<?>> entry : module.getDataComponentTypeRegistry().entrySet()) {
                        helper.register(entry.getKey(), entry.getValue());
                    }
                }
            });
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

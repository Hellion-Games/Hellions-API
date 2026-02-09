package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.MobEffectDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEffectRegistry;
import net.fabricmc.fabric.mixin.content.registry.BrewingRecipeRegistryBuilderMixin;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

import java.util.Map;

public class EffectModuleFabric {
    public static void registerEffects(String modid) {
        HellionsAPIEffectRegistry module = HellionsAPIEffectRegistry.getModule(modid);
        for (Map.Entry<ResourceLocation, MobEffectDataHolder<?>> entry : module.getEffectRegistry().entrySet()) {
            // Register effect
            Registry.register(BuiltInRegistries.MOB_EFFECT, entry.getKey(), entry.getValue().get());

            if (entry.getValue().hasPotion()) {
                String id = entry.getKey().getPath();
                String namespace = entry.getKey().getNamespace();

                Potion base = Registry.register(BuiltInRegistries.POTION, entry.getKey(),
                        new Potion(new MobEffectInstance(Holder.direct(entry.getValue().get()), 3600)));
                Potion longPotion = Registry.register(BuiltInRegistries.POTION, ResourceLocation.fromNamespaceAndPath(namespace, "long_" + id),
                        new Potion(id, new MobEffectInstance(Holder.direct(entry.getValue().get()), 9600)));
                Potion strong = Registry.register(BuiltInRegistries.POTION, ResourceLocation.fromNamespaceAndPath(namespace, "strong_" + id),
                        new Potion(id, new MobEffectInstance(Holder.direct(entry.getValue().get()), 1800, 1)));

                BrewingRecipeRegistryBuilderMixin.BUILD.register(builder -> {
                    builder.addMix(Potions.AWKWARD, entry.getValue().getPotionIngredient().get(), Holder.direct(base));
                    builder.addMix(Holder.direct(base), Items.REDSTONE, Holder.direct(longPotion));
                    builder.addMix(Holder.direct(base), Items.GLOWSTONE_DUST, Holder.direct(strong));
                });
            }
        }
    }
}

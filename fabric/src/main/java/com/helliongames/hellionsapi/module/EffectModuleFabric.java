package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.MobEffectDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEffectRegistry;
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
    public static void registerEffects() {
        for (HellionsAPIEffectRegistry module : HellionsAPIEffectRegistry.getModules()) {
            for (Map.Entry<ResourceLocation, MobEffectDataHolder<?>> entry : module.getEffectRegistry().entrySet()) {
                // Register effect
                Registry.register(BuiltInRegistries.MOB_EFFECT, entry.getKey(), entry.getValue().get());

                if (entry.getValue().hasPotion()) {
                    String id = entry.getKey().getPath();

                    Potion base = Registry.register(BuiltInRegistries.POTION, entry.getKey(),
                            new Potion(new MobEffectInstance(entry.getValue().get(), 3600)));
                    Potion long_ = Registry.register(BuiltInRegistries.POTION, IECommon.id("long_" + id),
                            new Potion(id, new MobEffectInstance(entry.getValue().get(), 9600)));
                    Potion strong = Registry.register(BuiltInRegistries.POTION, IECommon.id("strong_" + id),
                            new Potion(id, new MobEffectInstance(entry.getValue().get(), 1800, 1)));

                    PotionBrewing.addMix(Potions.AWKWARD, entry.getValue().getPotionIngredient().get(), base);
                    PotionBrewing.addMix(base, Items.REDSTONE, long_);
                    PotionBrewing.addMix(base, Items.GLOWSTONE_DUST, strong);
                }
            }
        }
    }
}

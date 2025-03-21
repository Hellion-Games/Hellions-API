package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.MobEffectDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEffectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import java.util.Map;

@EventBusSubscriber(modid = "hellionsapi", bus = EventBusSubscriber.Bus.GAME)
public class GameBusRegistryNeoForge {

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        for (HellionsAPIEffectRegistry module : HellionsAPIEffectRegistry.getModules()) {
            for (Map.Entry<ResourceLocation, MobEffectDataHolder<?>> entry : module.getEffectRegistry().entrySet()) {
                if (entry.getValue().hasPotion()) {
                    String id = entry.getKey().getPath();
                    String namespace = entry.getKey().getNamespace();

                    Potion base = BuiltInRegistries.POTION.get(entry.getKey());
                    Potion longPotion = BuiltInRegistries.POTION.get(ResourceLocation.fromNamespaceAndPath(namespace, "long_" + id));
                    Potion strong = BuiltInRegistries.POTION.get(ResourceLocation.fromNamespaceAndPath(namespace, "strong_" + id));

                    if (base == null || longPotion == null || strong == null) continue;

                    event.getBuilder().addMix(Potions.AWKWARD, entry.getValue().getPotionIngredient().get(), Holder.direct(base));
                    event.getBuilder().addMix(Holder.direct(base), Items.REDSTONE, Holder.direct(longPotion));
                    event.getBuilder().addMix(Holder.direct(base), Items.GLOWSTONE_DUST, Holder.direct(strong));
                }
            }
        }
    }
    
}

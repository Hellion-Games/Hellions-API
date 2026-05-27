package com.helliongames.hellionsapi.module;

import com.helliongames.hellionsapi.registration.holders.LootModifierDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPILootModifierRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import java.util.Map;

public class HellionsLootModifier implements IGlobalLootModifier {
    public static final MapCodec<HellionsLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(m -> m.id)
            ).apply(instance, HellionsLootModifier::new)
    );

    private final ResourceLocation id;

    public HellionsLootModifier(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ResourceLocation tableId = context.getQueriedLootTableId();

        for (HellionsAPILootModifierRegistry module : HellionsAPILootModifierRegistry.getModules().values()) {
            Map<ResourceLocation, LootModifierDataHolder> registry = module.getLootModifierRegistry();
            if (!registry.containsKey(this.id)) continue;

            LootModifierDataHolder holder = registry.get(this.id);
            if (!holder.getTargetTable().equals(tableId)) continue;

            holder.getEntry().get().build().expand(context, entry ->
                    entry.createItemStack(generatedLoot::add, context)
            );
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
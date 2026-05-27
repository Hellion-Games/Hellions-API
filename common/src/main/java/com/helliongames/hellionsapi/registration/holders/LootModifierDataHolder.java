package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

import java.util.function.Supplier;

public class LootModifierDataHolder {
    private final ResourceLocation targetTable;
    private final Supplier<LootPoolEntryContainer.Builder<?>> entry;

    public LootModifierDataHolder(ResourceLocation targetTable, Supplier<LootPoolEntryContainer.Builder<?>> entry) {
        this.targetTable = targetTable;
        this.entry = entry;
    }

    public static LootModifierDataHolder of(ResourceLocation targetTable, Supplier<LootPoolEntryContainer.Builder<?>> entry) {
        return new LootModifierDataHolder(targetTable, entry);
    }

    public ResourceLocation getTargetTable() {
        return targetTable;
    }

    public Supplier<LootPoolEntryContainer.Builder<?>> getEntry() {
        return entry;
    }
}
package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

import java.util.function.Supplier;

public class ArmorMaterialDataHolder {
    private ArmorMaterial cachedEntry;
    private final Supplier<ArmorMaterial> entrySupplier;

    public ArmorMaterialDataHolder(Supplier<ArmorMaterial> entrySupplier) {
        this.entrySupplier = entrySupplier;
    }

    public static ArmorMaterialDataHolder of(Supplier<ArmorMaterial> entrySupplier) {
        return new ArmorMaterialDataHolder(entrySupplier);
    }

    public ArmorMaterial get() {
        if (this.cachedEntry != null) return cachedEntry;
        ArmorMaterial entry = entrySupplier.get();
        this.cachedEntry = entry;
        return entry;
    }

    public Holder<ArmorMaterial> getHolder() {
        return Holder.direct(get());
    }
}
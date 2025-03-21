package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class MobEffectDataHolder<T extends MobEffect> {
    private T cachedEntry;
    private final Supplier<T> entrySupplier;

    private String defaultTranslation;
    private Supplier<Item> potionIngredient;

    public MobEffectDataHolder(Supplier<T> entrySupplier) {
        this.entrySupplier = entrySupplier;
    }

    public static MobEffectDataHolder<? extends MobEffect> of(Supplier<? extends MobEffect> supplier) {
        return new MobEffectDataHolder<>(supplier);
    }

    /**
     * Retrieves the cached entry if it exists, otherwise calls the supplier to create a new entry.
     * @return The cached entry, or a new entry if the cached entry does not exist.
     */
    public T get() {
        if (this.cachedEntry == null)
            this.cachedEntry = entrySupplier.get();

        return this.cachedEntry;
    }

    /**
     * Sets the default EN_US translation for this item
     * @param translation the name for this item
     */
    public MobEffectDataHolder<?> withTranslation(String translation) {
        this.defaultTranslation = translation;
        return this;
    }

    public boolean hasTranslation() {
        return this.defaultTranslation != null;
    }

    public String getTranslation() {
        return this.defaultTranslation;
    }

    public MobEffectDataHolder<?> withPotion(Supplier<Item> ingredient) {
        this.potionIngredient = ingredient;
        return this;
    }

    public boolean hasPotion() {
        return this.potionIngredient != null;
    }

    public Supplier<Item> getPotionIngredient() {
        return this.potionIngredient;
    }
}
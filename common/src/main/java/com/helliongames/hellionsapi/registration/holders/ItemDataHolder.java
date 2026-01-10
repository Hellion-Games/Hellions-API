package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ItemDataHolder<T extends Item> {
    private static final Map<TagKey<Item>, List<ItemDataHolder<?>>> ITEM_TAGS = new HashMap<>();

    private T cachedEntry;
    private final Supplier<T> entrySupplier;

    private ModelTemplate model;
    private String defaultTranslation;
    private int fuelDuration;

    public ItemDataHolder(Supplier<T> entrySupplier) {
        this.entrySupplier = entrySupplier;
    }

    public static <I extends Item> ItemDataHolder<I> of(Supplier<I> itemSupplier) {
        return new ItemDataHolder<>(itemSupplier);
    }

    /**
     * Retrieves the cached entry if it exists, otherwise calls the supplier to create a new entry.
     * @return The cached entry, or a new entry if the cached entry does not exist.
     */
    public T get() {
        if (this.cachedEntry != null) return cachedEntry;

        T entry = entrySupplier.get();
        this.cachedEntry = entry;

        return entry;
    }

    /**
     * Registers the item as a fuel source
     * @param fuelDuration the length in ticks this fuel source burns
     */
    public ItemDataHolder<T> withFuel(int fuelDuration) {
        this.fuelDuration = fuelDuration;
        return this;
    }

    public boolean isFuel() {
        return this.fuelDuration > 0;
    }

    public int getFuelDuration() {
        return this.fuelDuration;
    }

    /**
     * Registers this item to the supplied tags
     * @param tags the tag keys to register the item to
     */
    @SafeVarargs
    public final ItemDataHolder<T> withTags(TagKey<Item>... tags) {
        for (TagKey<Item> tag : tags) {
            ITEM_TAGS.putIfAbsent(tag, new ArrayList<>());
            ITEM_TAGS.get(tag).add(this);
        }

        return this;
    }

    public static Map<TagKey<Item>, List<ItemDataHolder<?>>> getItemTags() {
        return ITEM_TAGS;
    }

    /**
     * The model type of this item for datagen
     */
    public ItemDataHolder<T> withModel(ModelTemplate model) {
        this.model = model;
        return this;
    }

    public boolean hasModel() {
        return this.model != null;
    }

    public ModelTemplate getModel() {
        return this.model;
    }

    /**
     * Sets the default EN_US translation for this item
     * @param translation the name for this item
     */
    public ItemDataHolder<T> withTranslation(String translation) {
        this.defaultTranslation = translation;
        return this;
    }

    public boolean hasTranslation() {
        return this.defaultTranslation != null;
    }

    public String getTranslation() {
        return this.defaultTranslation;
    }
}
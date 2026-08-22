package com.helliongames.hellionsapi.registration.holders;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundDataHolder {

    private float range;
    private final boolean hasRange;
    private ResourceLocation resourceLocation;

    private SoundEvent cachedEntry;

    public SoundDataHolder() {
        this.hasRange = false;
    }

    public SoundDataHolder(float range) {
        this.range = range;
        this.hasRange = true;
    }

    public SoundEvent get() {
        if (this.cachedEntry != null) return this.cachedEntry;

        this.cachedEntry = this.hasRange() ?
                SoundEvent.createFixedRangeEvent(this.resourceLocation, this.getRange()) :
                SoundEvent.createVariableRangeEvent(this.resourceLocation);

        return this.cachedEntry;
    }

    public static SoundDataHolder of() {
        return new SoundDataHolder();
    }

    public static SoundDataHolder of(float range) {
        return new SoundDataHolder(range);
    }

    public boolean hasRange() {
        return this.hasRange;
    }

    public float getRange() {
        return this.range;
    }

    public void setResourceLocation(ResourceLocation location) {
        this.resourceLocation = location;
    }
}

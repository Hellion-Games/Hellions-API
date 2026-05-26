package com.helliongames.hellionsapi.registration.holders;

public class SoundDataHolder {

    private float range;
    private final boolean hasRange;

    public SoundDataHolder() {
        this.hasRange = false;
    }

    public SoundDataHolder(float range) {
        this.range = range;
        this.hasRange = true;
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
}

package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HellionsAPIEntityRegistry {
    private static final Map<String, HellionsAPIEntityRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPIEntityRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid, this);
    }

    /** Map of all EntityType Resource Locations to their EntityTypeDataHolders. */
    private final Map<ResourceLocation, EntityTypeDataHolder<? extends Entity>> ENTITY_TYPE_REGISTRY = new HashMap<>();

    /*
     public static final HellionsAPIEntityRegistry ENTITY_TYPE_MODULE = new HellionsAPIEntityRegistry("examplemod");

     public static final EntityTypeDataHolder EXAMPLE = ENTITY_TYPE_MODULE.register("example", EntityTypeDataHolder.of(() ->
                    EntityTypeDataHolder.Builder.of(ExampleEntity::new, MobCategory.CREATURE)
                            .sized(1.0f, 3.0f)
                            .build()
            )
            .attributes(ExampleEntity::createExampleEntityAttributes));
     */

    public <T extends Entity> EntityTypeDataHolder<T> register(String name, EntityTypeDataHolder<T> entityTypeDataHolder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.ENTITY_TYPE_REGISTRY.put(id, entityTypeDataHolder);
        return entityTypeDataHolder;
    }

    public Map<ResourceLocation, EntityTypeDataHolder<? extends Entity>> getEntityTypeRegistry() {
        return this.ENTITY_TYPE_REGISTRY;
    }

    public static Map<String, HellionsAPIEntityRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIEntityRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}
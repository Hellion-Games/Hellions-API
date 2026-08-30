package com.helliongames.hellionsapi.platform;

import com.helliongames.hellionsapi.module.*;
import com.helliongames.hellionsapi.platform.services.IRegistryHelper;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public void registerMod(String modid) {
        ArmorMaterialModuleFabric.registerArmorMaterials(modid);
        BlockModuleFabric.registerBlocks(modid);
        ItemModuleFabric.registerItems(modid);
        EntityTypeModuleFabric.registerEntities(modid);
        EffectModuleFabric.registerEffects(modid);
        DataComponentTypeModuleFabric.registerDataComponentTypes(modid);
        SoundModuleFabric.registerSounds(modid);
        ParticleModuleFabric.registerParticles(modid);
        LootModifierModuleFabric.registerLootModifiers(modid);
        MenuModuleFabric.registerMenus(modid);
        SpawnPlacementModuleFabric.registerSpawnPlacements(modid);
        BiomeModifierModuleFabric.registerBiomeModifiers(modid);
    }
}

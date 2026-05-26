package com.helliongames.hellionsapi.platform;

import com.helliongames.hellionsapi.module.*;
import com.helliongames.hellionsapi.platform.services.IRegistryHelper;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public void registerMod(String modid) {
        BlockModuleFabric.registerBlocks(modid);
        ItemModuleFabric.registerItems(modid);
        EntityTypeModuleFabric.registerEntities(modid);
        EffectModuleFabric.registerEffects(modid);
        SoundModuleFabric.registerSounds(modid);
    }
}

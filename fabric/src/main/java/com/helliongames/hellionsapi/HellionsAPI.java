package com.helliongames.hellionsapi;

import com.helliongames.hellionsapi.module.BlockModuleFabric;
import com.helliongames.hellionsapi.module.EffectModuleFabric;
import com.helliongames.hellionsapi.module.EntityTypeModuleFabric;
import com.helliongames.hellionsapi.module.ItemModuleFabric;
import net.fabricmc.api.ModInitializer;

public class HellionsAPI implements ModInitializer {
    
    @Override
    public void onInitialize() {
        HellionsAPICommon.init();

        BlockModuleFabric.registerBlocks();
        ItemModuleFabric.registerItems();
        EntityTypeModuleFabric.registerEntities();
        EffectModuleFabric.registerEffects();
    }
}

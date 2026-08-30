package com.helliongames.hellionsapi.platform;

import com.helliongames.hellionsapi.client.HellionsAPIClient;
import com.helliongames.hellionsapi.platform.services.IClientRegistryHelper;

public class FabricClientRegistryHelper implements IClientRegistryHelper {
    @Override
    public void registerMod(String modid) {
        HellionsAPIClient.init(modid);
    }
}

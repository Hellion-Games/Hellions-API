package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.platform.Services;

public class HellionsAPICommonClient {
    public static void init(String modid) {
        Services.CLIENT_REGISTRY.registerMod(modid);
    }
}

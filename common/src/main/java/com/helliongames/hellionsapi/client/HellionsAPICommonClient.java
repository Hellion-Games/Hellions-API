package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRendererRegistry;

public class HellionsAPICommonClient {
    public static void init() {
        HellionsAPIEntityRendererRegistry.loadClass();
    }
}

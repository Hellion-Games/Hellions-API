package com.helliongames.hellionsapi.client;

import com.helliongames.hellionsapi.registration.holders.EntityTypeDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

import java.util.Map;

public class HellionsAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HellionsAPICommonClient.init();
        registerEntityRenderers();
    }

    private void registerEntityRenderers() {
        HellionsAPIEntityRendererRegistry.forEachEntry(entry -> EntityRendererRegistry.register(entry.getHolder().get(), entry.getProvider()));
    }

}

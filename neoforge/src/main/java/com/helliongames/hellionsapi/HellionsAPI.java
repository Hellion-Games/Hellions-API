package com.helliongames.hellionsapi;


import com.helliongames.hellionsapi.client.HellionsAPINeoForgeClient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(HellionsAPIConstants.MOD_ID)
public class HellionsAPI {

    public HellionsAPI(IEventBus eventBus) {
        if (FMLEnvironment.dist.isClient()) {
            HellionsAPINeoForgeClient.init(eventBus);
        }
    }
}
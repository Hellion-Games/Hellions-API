package com.helliongames.hellionsapi.platform;

import com.helliongames.hellionsapi.HellionsAPIConstants;
import com.helliongames.hellionsapi.platform.services.IClientRegistryHelper;
import com.helliongames.hellionsapi.platform.services.IPlatformHelper;
import com.helliongames.hellionsapi.platform.services.IRegistryHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);
    public static final IClientRegistryHelper CLIENT_REGISTRY = load(IClientRegistryHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        HellionsAPIConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
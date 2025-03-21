package com.helliongames.hellionsapi.registration;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StrippableRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(StrippableRegistry.class);
    private static final Map<Block, Block> REGISTRY = new HashMap<>();

    public static void register(Block input, Block stripped) {
        requireNonNullAndAxisProperty(input, "input block");
        requireNonNullAndAxisProperty(stripped, "stripped block");

        Block prev = REGISTRY.put(input, stripped);

        if (prev != null) {
            LOGGER.debug("Replaced block {} stripping to {} with {}", input, prev, stripped);
        }
    }

    private static void requireNonNullAndAxisProperty(Block block, String name) {
        Objects.requireNonNull(block, name + " cannot be null");

        if (!block.getStateDefinition().getProperties().contains(BlockStateProperties.AXIS)) {
            throw new IllegalArgumentException(name + " must have the 'axis' property");
        }
    }

    public static Block get(Block input) {
        return REGISTRY.get(input);
    }
}

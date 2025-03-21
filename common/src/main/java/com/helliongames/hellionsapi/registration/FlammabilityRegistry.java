package com.helliongames.hellionsapi.registration;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

import java.util.HashMap;
import java.util.Map;

public class FlammabilityRegistry {
    private static final Map<Block, FlammabilityRegistry> REGISTRIES = new HashMap<>();

    private final Map<Block, Entry> blockToFlammability = new HashMap<>();
    private final Block key;

    private FlammabilityRegistry(Block key) {
        this.key = key;
    }

    public Map<Block, Entry> getFlammabilityMap() {
        return this.blockToFlammability;
    }

    public Entry get(Block block) {
        return this.blockToFlammability.get(block);
    }

    public void register(Block block, Entry entry) {
        this.blockToFlammability.put(block, entry);
    }

    public static void registerDefault(Block block, Entry entry) {
        REGISTRIES.computeIfAbsent(Blocks.FIRE, FlammabilityRegistry::new).register(block, entry);
    }

    public static FlammabilityRegistry getRegistry(Block fireBlock) {
        if (!(fireBlock instanceof FireBlock)) {
            throw new RuntimeException("Not a fire block: " + fireBlock);
        }

        return REGISTRIES.computeIfAbsent(fireBlock, FlammabilityRegistry::new);
    }

    public record Entry(int igniteChance, int spreadChance) {
    }
}

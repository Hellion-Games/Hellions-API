package com.helliongames.hellionsapi.registration.registries;

import com.helliongames.hellionsapi.registration.holders.BlockDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HellionsAPIBlockRegistry {
    private static final Map<String, HellionsAPIBlockRegistry> MODULES = new HashMap<>();

    private final String modid;

    public HellionsAPIBlockRegistry(String modid) {
        this.modid = modid;
        MODULES.put(modid,this);
    }

    /** Map of all Block Resource Locations to their BlockDataHolders. */
    private final Map<ResourceLocation, BlockDataHolder<? extends Block>> BLOCK_REGISTRY = new HashMap<>();

    /*
    public static final HellionsAPIBlockRegistry BLOCK_MODULE = new HellionsAPIBlockRegistry("examplemod");

    public static final BlockDataHolder<?> EXAMPLE_BLOCK = BLOCK_MODULE.register("example_block", BlockDataHolder.of(() ->
                    new SoulSandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)))
            .withModel(BlockDataHolder.Model.ROTATABLE).withItem().dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.INFINIBURN_NETHER)
            .withTranslation("Example Block")
    );
    */

    public <T extends Block> BlockDataHolder<T> register(String name, BlockDataHolder<T> blockDataHolder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
        this.BLOCK_REGISTRY.put(id, blockDataHolder);
        return blockDataHolder;
    }

    public Map<ResourceLocation, BlockDataHolder<? extends Block>> getBlockRegistry() {
        return this.BLOCK_REGISTRY;
    }

    public static Map<String, HellionsAPIBlockRegistry> getModules() {
        return MODULES;
    }

    public static HellionsAPIBlockRegistry getModule(String modid) {
        return MODULES.get(modid);
    }
}

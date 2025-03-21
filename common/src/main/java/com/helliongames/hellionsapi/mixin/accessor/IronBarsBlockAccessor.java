package com.helliongames.hellionsapi.mixin.accessor;

import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(IronBarsBlock.class)
public interface IronBarsBlockAccessor {
    @Invoker("<init>")
    static IronBarsBlock createIronBarsBlock(BlockBehaviour.Properties properties) {
        throw new UnsupportedOperationException();
    }
}

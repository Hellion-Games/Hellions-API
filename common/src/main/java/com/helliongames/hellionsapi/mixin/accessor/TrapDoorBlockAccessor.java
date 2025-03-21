package com.helliongames.hellionsapi.mixin.accessor;

import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TrapDoorBlock.class)
public interface TrapDoorBlockAccessor {
    @Invoker("<init>")
    static TrapDoorBlock createTrapDoorBlock(BlockSetType blockSetType, BlockBehaviour.Properties properties) {
        throw new UnsupportedOperationException();
    }
}

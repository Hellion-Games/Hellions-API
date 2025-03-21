package com.helliongames.hellionsapi.mixin.accessor;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockBehaviour.class)
public interface BlockBehaviourAccessor {

    @Invoker
    float invokeGetMaxHorizontalOffset();

    @Invoker
    float invokeGetMaxVerticalOffset();
}

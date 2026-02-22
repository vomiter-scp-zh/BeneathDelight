package com.vomiter.beneathdelight.common.food.block;

import com.vomiter.beneathdelight.registry.ModBlockEntityTypes;
import com.vomiter.survivorsdelight.common.food.block.SDDecayingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DecayingMagmaCakeBlockEntity  extends SDDecayingBlockEntity {
    public DecayingMagmaCakeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.MAGMA_CAKE_DECAYING.get(), pos, state);
    }
}

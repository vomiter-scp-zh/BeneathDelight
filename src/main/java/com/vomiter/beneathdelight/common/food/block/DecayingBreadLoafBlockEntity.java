package com.vomiter.beneathdelight.common.food.block;

import com.vomiter.beneathdelight.common.registry.ModBlockEntityTypes;
import com.vomiter.survivorsdelight.common.food.block.SDDecayingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DecayingBreadLoafBlockEntity extends SDDecayingBlockEntity {
    public DecayingBreadLoafBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.BREAD_LOAF_DECAYING.get(), pos, state);
    }
}

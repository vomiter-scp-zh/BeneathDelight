package com.vomiter.beneathdelight.mixin.farming;

import com.soytutta.mynethersdelight.common.block.ResurgentSoilFarmlandBlock;
import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ResurgentSoilFarmlandBlock.class, remap = false)
public class ResurgentFarmlandMixin {
    @Inject(method = "performBonemealIfPossible", at = @At("HEAD"), cancellable = true)
    private void preventBoneMeal(Block block, BlockPos position, BlockState state, ServerLevel level, int distance, CallbackInfo ci){
        var blockentity = level.getBlockEntity(position);
        if(blockentity instanceof CropBlockEntity) ci.cancel();
        if(blockentity instanceof TickCounterBlockEntity) ci.cancel();
    }
}

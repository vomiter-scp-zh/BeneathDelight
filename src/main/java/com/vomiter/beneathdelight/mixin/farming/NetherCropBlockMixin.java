package com.vomiter.beneathdelight.mixin.farming;

import com.eerussianguy.beneath.common.blocks.NetherCropBlock;
import com.soytutta.mynethersdelight.common.block.ResurgentSoilFarmlandBlock;
import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherCropBlock.class)
public class NetherCropBlockMixin {
    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    private void canSurviveOnResurgentFarm(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue()) return;
        if(level.getBlockState(pos.below()).is(MNDBlocks.RESURGENT_SOIL_FARMLAND.get())) cir.setReturnValue(true);
    }
}

package com.vomiter.beneathdelight.mixin.food.block;

import com.soytutta.mynethersdelight.common.block.GhastaWithCreamBlock;
import com.vomiter.survivorsdelight.common.food.block.SDDecayingBlockEntity;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.IFood;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GhastaWithCreamBlock.class)
public class GhastaWithCreamBlock_NeverExpireMixin {
    @Inject(method = "randomTick", at = @At("RETURN"))
    private void bdtfc$neverExpire(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random, CallbackInfo ci){
        var blockEntity = worldIn.getBlockEntity(pos);
        if(blockEntity instanceof SDDecayingBlockEntity decay){
            IFood food = FoodCapability.get(decay.getStack());
            if(food != null && food.isRotten()){
                food.setCreationDate(worldIn.getDayTime());

            }
        }
    }
}

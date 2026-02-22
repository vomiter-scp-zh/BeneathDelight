package com.vomiter.beneathdelight.mixin.farming;

import com.eerussianguy.beneath.misc.NCropUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = NCropUtil.class, remap = false)
public class NCropUtilMixin {
    @WrapOperation(method = "growthTickStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/DimensionType;ultraWarm()Z", remap = true))
    private static boolean skipUltraWarmIfResurgent(
            DimensionType instance, Operation<Boolean> original,
            @Local(argsOnly = true)Level level, @Local(argsOnly = true)BlockPos pos)
    {
        //pos is the pos of crop, we need to check the soil, which is below
        if(level.getBlockState(pos.below()).is(MNDBlocks.RESURGENT_SOIL_FARMLAND.get())){
            return true;
        }
        return original.call(instance);
    }
}

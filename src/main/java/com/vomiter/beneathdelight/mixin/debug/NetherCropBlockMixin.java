package com.vomiter.beneathdelight.mixin.debug;

import com.eerussianguy.beneath.common.blocks.NetherCropBlock;
import com.eerussianguy.beneath.misc.NCropUtil;
import com.vomiter.beneathdelight.BeneathDelight;
import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetherCropBlock.class, remap = false)
public class NetherCropBlockMixin {
    @Inject(method = "growthTick", at = @At("HEAD"))
    private void growthHead(Level level, BlockPos pos, BlockState state, CropBlockEntity crop, CallbackInfo ci){
        if(level.isClientSide) return;
        //BeneathDelight.LOGGER.info("running growth for {}", state.getBlock().getName().getString());
        //BeneathDelight.LOGGER.info("should grow: {}", NCropUtil.growthTick(level, pos, state, crop));
    }
}

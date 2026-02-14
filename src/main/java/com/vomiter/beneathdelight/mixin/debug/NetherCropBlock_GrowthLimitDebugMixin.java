package com.vomiter.beneathdelight.mixin.debug;

import com.eerussianguy.beneath.common.blocks.NetherCropBlock;
import com.vomiter.beneathdelight.BeneathDelight;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NetherCropBlock.class, remap = false)
public abstract class NetherCropBlock_GrowthLimitDebugMixin {

}

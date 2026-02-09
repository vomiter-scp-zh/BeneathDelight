package com.vomiter.beneathdelight.mixin.food.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.soytutta.mynethersdelight.common.block.StriderloafBlock;
import com.vomiter.beneathdelight.common.util.DelayedStriderLoafTask;
import com.vomiter.survivorsdelight.common.food.block.DecayFoodTransfer;
import com.vomiter.survivorsdelight.common.food.block.DecayingFeastBlockEntity;
import com.vomiter.survivorsdelight.util.HeatHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = StriderloafBlock.class, remap = false)
public class StriderLoafBlockMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lcom/soytutta/mynethersdelight/common/block/StriderloafBlock;updateBlockState(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/sounds/SoundEvent;)V"))
    private void setStack(StriderloafBlock instance, ServerLevel worldIn, BlockPos pos, BlockState newState, SoundEvent sound, Operation<Void> original){
        if(!(worldIn.getBlockEntity(pos) instanceof DecayingFeastBlockEntity decay)) {
            original.call(instance, worldIn, pos, newState, sound);
            return;
        }
        ItemStack previousStack = decay.getStack();
        ItemStack targetStack = new ItemStack(newState.getBlock().asItem());
        DecayFoodTransfer.copyFoodState(previousStack, targetStack, false);
        worldIn.setBlock(pos, newState, 3);
        original.call(instance, worldIn, pos, newState, sound);
        if(!(worldIn.getBlockEntity(pos) instanceof DecayingFeastBlockEntity targetDecay)) {
            return;
        }
        targetDecay.setStack(targetStack);
        targetDecay.markForBlockUpdate();
        targetDecay.sendVanillaUpdatePacket();
        DelayedStriderLoafTask.scheduleStriderLoafTask(
                worldIn,
                pos,
                targetStack,
                newState.getBlock(), // expectedBlock 防呆
                1                   // 延後 1 tick
        );
    }

    @WrapOperation(method = "hasLava", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private static boolean readHeatSource(
            BlockState instance, TagKey tagKey, Operation<Boolean> original,
            @Local(argsOnly = true, name = "arg0") LevelReader level, @Local(argsOnly = true, name = "arg1") BlockPos pos
    ){
        return HeatHelper.getTargetTemperature(pos, level, true, HeatHelper.GetterType.BOTH) >= 100;
    }
}

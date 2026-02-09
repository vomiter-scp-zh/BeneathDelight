package com.vomiter.beneathdelight.mixin.food.block;

import com.soytutta.mynethersdelight.common.block.StuffedHoglinBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = StuffedHoglinBlock.class, remap = false)
public abstract class StuffedHoglinBlock_BreakRedirectMixin {

    @Inject(method = "playerWillDestroy", at = @At("HEAD"), cancellable = true, remap = true)
    private void sdtfc$redirectBreak(Level level, BlockPos pos, BlockState state, Player player, CallbackInfo ci) {
        if (level.isClientSide) return;
        if (player.isCreative()) return;

        if (state.getValue(StuffedHoglinBlock.PART) == BedPart.FOOT) {
            BlockPos headPos = sdtfc$getHeadPos(state, pos);

            // 1) 先把 FOOT 清掉，避免後面 destroy head 時觸發額外連鎖/重複掉落
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);

            // 2) 用 destroyBlock(headPos, true) 讓掉落一定從 HEAD 的 BE/狀態走
            level.destroyBlock(headPos, true, player);

            ci.cancel();
        }
    }

    @Unique
    private static BlockPos sdtfc$getHeadPos(BlockState state, BlockPos pos) {
        BedPart part = state.getValue(StuffedHoglinBlock.PART);
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        return part == BedPart.HEAD ? pos : pos.relative(facing.getOpposite());
    }
}

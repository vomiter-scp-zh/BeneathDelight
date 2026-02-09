package com.vomiter.beneathdelight.mixin.food.block;

import com.soytutta.mynethersdelight.common.block.MagmaCakeBlock;
import com.vomiter.beneathdelight.common.food.block.DecayingMagmaCakeBlockEntity;
import com.vomiter.beneathdelight.common.registry.ModBlockEntityTypes;
import com.vomiter.survivorsdelight.common.food.block.ISDDecayingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagmaCakeBlock.class)
public class MagmaCakeBlock_BlockEntityMixin  extends Block implements EntityBlock, ISDDecayingBlock {
    public MagmaCakeBlock_BlockEntityMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new DecayingMagmaCakeBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == ModBlockEntityTypes.MAGMA_CAKE_DECAYING.get()
                ? (l, p, st, be) -> DecayingMagmaCakeBlockEntity.serverTick(l, p, st, (DecayingMagmaCakeBlockEntity) be)
                : null;
    }

    @Override
    public int sdtfc$getServingCount(BlockState state) {
        if(!(state.getBlock() instanceof MagmaCakeBlock magmaCakeBlock)) return 0;
        int remaining = magmaCakeBlock.getMaxBites() - state.getValue(MagmaCakeBlock.BITES);
        if (state.getValue(MagmaCakeBlock.SECOND_CAKE)) {
            remaining += magmaCakeBlock.getMaxBites();
        }
        return remaining;
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void sdtfc$use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        sdtfc$useGlue(state, level, pos, player, hand, hit, cir);
    }
}

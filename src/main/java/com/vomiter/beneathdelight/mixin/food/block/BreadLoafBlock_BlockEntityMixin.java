package com.vomiter.beneathdelight.mixin.food.block;

import com.soytutta.mynethersdelight.common.block.BreadLoafBlock;
import com.vomiter.beneathdelight.common.food.block.DecayingBreadLoafBlockEntity;
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

@Mixin(BreadLoafBlock.class)
public class BreadLoafBlock_BlockEntityMixin extends Block implements EntityBlock, ISDDecayingBlock {
    public BreadLoafBlock_BlockEntityMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new DecayingBreadLoafBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == ModBlockEntityTypes.BREAD_LOAF_DECAYING.get()
                ? (l, p, st, be) -> DecayingBreadLoafBlockEntity.serverTick(l, p, st, (DecayingBreadLoafBlockEntity) be)
                : null;
    }

    @Override
    public int sdtfc$getServingCount(BlockState state) {
        if(!(state.getBlock() instanceof BreadLoafBlock breadLoafBlock)) return 0;
        return breadLoafBlock.getMaxBites() - state.getValue(BreadLoafBlock.BITES);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void sdtfc$use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        sdtfc$useGlue(state, level, pos, player, hand, hit, cir);
    }

}

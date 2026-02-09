package com.vomiter.beneathdelight.mixin.food.block;

import com.soytutta.mynethersdelight.common.block.StuffedHoglinBlock;
import com.vomiter.beneathdelight.common.food.block.DecayingStuffedHoglinBlockEntity;
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
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = StuffedHoglinBlock.class, remap = false)
public abstract class StuffedHoglinBlock_BlockEntityMixin extends Block implements EntityBlock, ISDDecayingBlock {

    protected StuffedHoglinBlock_BlockEntityMixin(Properties props) { super(props); }

    @Shadow
    public abstract IntegerProperty getServingsProperty();

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        // 兩半都給 BE：確保不論玩家打哪半都能在 getDrops 拿到 decay state
        return new DecayingStuffedHoglinBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (type != ModBlockEntityTypes.STUFFED_HOGLIN_DECAYING.get()) return null;

        // 僅 HEAD tick：避免兩半都 tick 造成 double-update
        return state.getValue(StuffedHoglinBlock.PART) == BedPart.HEAD
                ? (l, p, st, be) -> DecayingStuffedHoglinBlockEntity.serverTick(l, p, st, (DecayingStuffedHoglinBlockEntity) be)
                : null;
    }

    @Override
    public int sdtfc$getServingCount(BlockState state) {
        return state.getValue(getServingsProperty());
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void sdtfc$use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        sdtfc$useGlue(state, level, pos, player, hand, hit, cir);
    }

}

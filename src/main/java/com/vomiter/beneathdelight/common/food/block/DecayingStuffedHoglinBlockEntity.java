package com.vomiter.beneathdelight.common.food.block;

import com.soytutta.mynethersdelight.common.block.StuffedHoglinBlock;
import com.vomiter.beneathdelight.common.registry.ModBlockEntityTypes;
import com.vomiter.survivorsdelight.common.food.block.SDDecayingBlockEntity;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import org.jetbrains.annotations.NotNull;

public class DecayingStuffedHoglinBlockEntity extends SDDecayingBlockEntity {
    public DecayingStuffedHoglinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.STUFFED_HOGLIN_DECAYING.get(), pos, state);
    }
    private boolean foodRotten = false;
    private boolean needsInitialSync = true;
    public static void serverTick(Level level, BlockPos pos, BlockState state, DecayingStuffedHoglinBlockEntity be) {
        if (be.needsInitialSync) {
            be.needsInitialSync = false;
            level.sendBlockUpdated(pos, state, state, 3);
        }
        if (level.getGameTime() % 20L == 0L && be.isRotten() && !be.foodRotten) {
            be.foodRotten = true;
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag) {
        super.loadAdditional(tag);
        this.needsInitialSync = true;
    }

    @Override
    public @NotNull ItemStack getStack(){
        if(this.getBlockPos().equals(getHeadPos())) return super.getStack();
        var level = getLevel();
        if(level != null && level.getBlockEntity(getHeadPos()) instanceof DecayingStuffedHoglinBlockEntity decay){
            return decay.getStack();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isRotten() {
        return getStack().isEmpty() || FoodCapability.isRotten(getStack());
    }

    public BlockPos getHeadPos() {
        var state = getBlockState();
        var pos = getBlockPos();
        BedPart part = state.getValue(StuffedHoglinBlock.PART);
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        return part == BedPart.HEAD ? pos : pos.relative(facing.getOpposite());
    }

}

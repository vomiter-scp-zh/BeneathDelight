package com.vomiter.beneathdelight.mixin.stove;

import com.soytutta.mynethersdelight.common.block.NetherStoveBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = NetherStoveBlock.class, remap = false)
public class NetherStoveBlock_FuelAndHeat {
    /*
    @Inject(method = "use", at = @At("HEAD"), cancellable = true, remap = true)
    private void addFuel(
            BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir
            )
    {
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof IStoveBlockEntity stoveEntity) {
            ItemStack heldItem = player.getItemInHand(hand);
            Fuel fuel = Fuel.get(heldItem);
            float logBonus = heldItem.is(TFCTags.Items.FIREPIT_LOGS)? 1.2f: 1;
            if(fuel != null){
                if(stoveEntity.sdtfc$getLeftBurnTick() > IStoveBlockEntity.sdtfc$getMaxDuration()) return;
                stoveEntity.sdtfc$addLeftBurnTick(Math.round(fuel.getDuration() * logBonus * fuel.getTemperature() * 6 / IStoveBlockEntity.sdtfc$getStaticTemperature()));
                cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }
     */

    /*
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"), cancellable = true, remap = true)
    private void addFood(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir){
        ItemStack heldItem = player.getItemInHand(hand);
        NetherStoveBlockEntity stove = (NetherStoveBlockEntity) level.getBlockEntity(pos);
        INetherStoveBlockEntity iStove = (INetherStoveBlockEntity) stove;
        assert iStove != null;
        if(iStove.sdtfc$addItem(heldItem, stove.getNextEmptySlot(), iStove, player)) cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
    }
    */
}

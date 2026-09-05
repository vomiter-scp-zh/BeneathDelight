package com.vomiter.beneathdelight.mixin.food.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.soytutta.mynethersdelight.common.block.StuffedHoglinBlock;
import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.beneathdelight.common.food.block.DecayingStuffedHoglinBlockEntity;
import com.vomiter.survivorsdelight.common.food.block.DecayFoodTransfer;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.FarmersDelight;


@Mixin(value = StuffedHoglinBlock.class, remap = false)
public abstract class StuffedHoglinBlock_ServingMixin {

    @WrapOperation(
            method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", remap = true),
            remap = true
    )
    private boolean sdtfc$acceptCeramicBowl(ItemStack instance, Item itemLike, Operation<Boolean> original
    ) {
        if (itemLike == Items.BOWL && instance.is(TFCBlocks.CERAMIC_BOWL.get().asItem())) {
            return true;
        }
        return original.call(instance, itemLike);
    }

    @WrapOperation(
            method = "takeServing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z",
                    remap = true
            )
    )
    private boolean sdtfc$patchServingStack(
            Inventory instance,
            ItemStack stack,
            Operation<Boolean> original,
            @Local(argsOnly = true) Level level,
            @Local(argsOnly = true) BlockPos pos,
            @Local(argsOnly = true) BlockState state,
            @Local(argsOnly = true) Player player,
            @Local(argsOnly = true) InteractionHand hand
    ) {
        // 正規化到 HEAD
        BlockEntity thisBe = level.getBlockEntity(pos);
        if(!(thisBe instanceof DecayingStuffedHoglinBlockEntity thisDecay)) return original.call(instance, stack);
        BlockPos headPos = thisDecay.getHeadPos();
        if(!headPos.equals(pos)){
            thisBe = level.getBlockEntity(headPos);
            if (!(thisBe instanceof DecayingStuffedHoglinBlockEntity)) return original.call(instance, stack);
            thisDecay = (DecayingStuffedHoglinBlockEntity)thisBe;
        }

        ItemStack src = thisDecay.getStack();
        float factor = 0f;
        if(stack.is(MNDItems.PLATE_OF_STUFFED_HOGLIN_SNOUT.get())) factor = 0.5f;
        else if(stack.is(MNDItems.PLATE_OF_STUFFED_HOGLIN_HAM.get())) factor = 0.3f;
        else if(stack.is(MNDItems.PLATE_OF_STUFFED_HOGLIN.get())) factor = 0.2f;
        BeneathDelight.LOGGER.debug("src = {}, stack = {}, factor = {}", src, stack, factor);

        DecayFoodTransfer.copyFoodState(src, stack, true, factor);

        if (player.getItemInHand(hand).is(TFCBlocks.CERAMIC_BOWL.get().asItem())) {
            stack.getOrCreateTag().put("Container", player.getItemInHand(hand).copyWithCount(1).serializeNBT());
        }

        return original.call(instance, stack);
    }
}

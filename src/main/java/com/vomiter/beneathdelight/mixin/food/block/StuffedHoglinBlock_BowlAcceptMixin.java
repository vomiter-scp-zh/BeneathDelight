package com.vomiter.beneathdelight.mixin.food.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.soytutta.mynethersdelight.common.block.StuffedHoglinBlock;
import com.vomiter.beneathdelight.common.food.block.DecayingStuffedHoglinBlockEntity;
import com.vomiter.survivorsdelight.common.food.block.DecayFoodTransfer;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(value = StuffedHoglinBlock.class, remap = false)
public abstract class StuffedHoglinBlock_BowlAcceptMixin {

    @WrapOperation(
            method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", remap = true)
    )
    private boolean sdtfc$acceptCeramicBowl(ItemStack instance, Item itemLike, Operation<Boolean> original
    ) {
        // 只在比較「Items.BOWL」那次放行：避免把其他 is(...) 全部污染
        if (itemLike == Items.BOWL && instance.is(TFCBlocks.CERAMIC_BOWL.get().asItem())) {
            return true;
        }
        return original.call(instance, itemLike);
    }

    @ModifyVariable(
            method = "takeServing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z", remap = true),
            // 這裡的 index/name 要用你實際的 LVT 來對
            ordinal = 0
    )
    private ItemStack sdtfc$patchServingStack(
            ItemStack servingStack,
            @Local(argsOnly = true) Level level,
            @Local(argsOnly = true) BlockPos pos,
            @Local(argsOnly = true) BlockState state,
            @Local(argsOnly = true) Player player,
            @Local(argsOnly = true) InteractionHand hand
    ) {
        // 1) 正規化到 HEAD
        BlockEntity thisBe = level.getBlockEntity(pos);
        if(!(thisBe instanceof DecayingStuffedHoglinBlockEntity thisDecay)) return servingStack;
        BlockPos headPos = thisDecay.getHeadPos();
        if(!headPos.equals(pos)){
            thisBe = level.getBlockEntity(headPos);
            if (!(thisBe instanceof DecayingStuffedHoglinBlockEntity)) return servingStack;
            thisDecay = (DecayingStuffedHoglinBlockEntity)thisBe;
        }

        // 2) 把腐壞/食材/traits/creationDate 搬到 servingStack（沿用你現有 helper）
        ItemStack src = thisDecay.getStack();
        DecayFoodTransfer.copyFoodState(src, servingStack, true);

        // 3) 如果是陶碗，寫 Container tag（沿用你 Feast 的作法）
        if (player.getItemInHand(hand).is(TFCBlocks.CERAMIC_BOWL.get().asItem())) {
            servingStack.getOrCreateTag().put("Container", player.getItemInHand(hand).copyWithCount(1).serializeNBT());
        }

        return servingStack;
    }

}

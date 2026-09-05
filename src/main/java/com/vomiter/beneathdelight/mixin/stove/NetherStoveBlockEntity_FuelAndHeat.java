package com.vomiter.beneathdelight.mixin.stove;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.soytutta.mynethersdelight.common.block.NetherStoveBlock;
import com.soytutta.mynethersdelight.common.block.entity.NetherStoveBlockEntity;
import com.vomiter.beneathdelight.adapter.INetherStoveBlockEntity;
import com.vomiter.beneathdelight.compat.NetherStoveOvenCompat;
import com.vomiter.survivorsdelight.HeatSourceBlockEntity;
import com.vomiter.survivorsdelight.adapter.stove.IStoveBlockEntity;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.utility.ItemUtils;

@Mixin(value = NetherStoveBlockEntity.class, remap = false)
public abstract class NetherStoveBlockEntity_FuelAndHeat implements HeatSourceBlockEntity, IStoveBlockEntity {
    @Unique private static final String SD_LEFT_BURN_TICK = "SDLeftBurnTick";
    @Unique private int leftBurnTick = Integer.MAX_VALUE;
    @Unique private final HeatingRecipe[] cachedHeatingRecipes = new HeatingRecipe[6];

    public float sdtfc$getTemperature(){
        if(!((BlockEntity)(Object)this).getBlockState().getValue(NetherStoveBlock.LIT)) return 0;
        return INetherStoveBlockEntity.sdtfc$getStaticTemperature();
    }

    @Unique
    public int sdtfc$getLeftBurnTick(){return leftBurnTick;}

    @Unique
    public void sdtfc$setLeftBurnTick(int v){leftBurnTick = Integer.MAX_VALUE;}

    @Override
    public HeatingRecipe[] sdtfc$getCachedRecipes() {
        return cachedHeatingRecipes;
    }

    @Override
    public void sdtfc$reduceLeftBurnTick(int i){

    }
}

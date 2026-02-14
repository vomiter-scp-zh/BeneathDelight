package com.vomiter.beneathdelight.mixin.debug;

import com.eerussianguy.beneath.common.blockentities.SoulFarmlandBlockEntity;
import com.eerussianguy.beneath.common.blocks.NetherCropBlock;
import com.eerussianguy.beneath.misc.NCropUtil;
import com.vomiter.beneathdelight.BeneathDelight;
import com.llamalad7.mixinextras.sugar.Local;
import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NCropUtil.class, remap = false)
public abstract class NCropUtilMixin {

    @Inject(method = "growthTickStep", at = @At("RETURN"))
    private static void bd$logGrowthTickStepFail(
            Level level, BlockPos pos, BlockState state, RandomSource random,
            long fromTick, long toTick, CropBlockEntity crop,
            CallbackInfoReturnable<Boolean> cir,

            // ---- locals：用 name 抓，比用 ordinal 穩定 ----
            @Local(name = "cropBlock") NetherCropBlock cropBlock,
            @Local(name = "growing") boolean growing,

            @Local(name = "primaryNutrient") SoulFarmlandBlockEntity.NutrientType primaryNutrient,
            @Local(name = "nutrientsAvailable") float nutrientsAvailable,
            @Local(name = "nutrientsRequired") float nutrientsRequired,
            @Local(name = "nutrientsConsumed") float nutrientsConsumed,

            @Local(name = "growthModifier") float growthModifier,
            @Local(name = "expiryModifier") float expiryModifier,
            @Local(name = "localExpiryLimit") float localExpiryLimit,

            @Local(name = "totalGrowthDelta") float totalGrowthDelta,
            @Local(name = "initialGrowth") float initialGrowth,
            @Local(name = "growth") float growth,
            @Local(name = "expiry") float expiry,
            @Local(name = "actualYield") float actualYield,
            @Local(name = "growthLimit") float growthLimit
    ) {
        if (cir.getReturnValue()) return; // 只追 false

        final long tickDelta = toTick - fromTick;
        final ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(state.getBlock());
        final boolean expiryReached = expiry >= localExpiryLimit;

        BeneathDelight.LOGGER.warn(
                "[NCropUtil] growthTickStep => FALSE | dimUltraWarm={} growing={} expiryReached={} | pos={} block={} | from={} to={} Δ={} | " +
                        "growth: initial={} -> {} (limit={}) | expiry={} / {} | yield={} | " +
                        "nutrient: primary={} avail={} req={} consumed={} | " +
                        "mods: growthMod={} expiryMod={} totalGrowthDelta={}",
                level.dimensionType().ultraWarm(), growing, expiryReached,
                pos, blockId,
                fromTick, toTick, tickDelta,
                initialGrowth, growth, growthLimit,
                expiry, localExpiryLimit,
                actualYield,
                primaryNutrient, nutrientsAvailable, nutrientsRequired, nutrientsConsumed,
                growthModifier, expiryModifier, totalGrowthDelta
        );
    }
}

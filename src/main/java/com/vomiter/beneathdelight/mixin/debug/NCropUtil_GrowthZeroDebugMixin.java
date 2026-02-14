package com.vomiter.beneathdelight.mixin.debug;

import com.eerussianguy.beneath.misc.NCropUtil;
import com.eerussianguy.beneath.common.blocks.NetherCropBlock;
import com.eerussianguy.beneath.common.blockentities.SoulFarmlandBlockEntity;
import com.llamalad7.mixinextras.sugar.Local;
import com.vomiter.beneathdelight.BeneathDelight;
import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NCropUtil.class, remap = false)
public abstract class NCropUtil_GrowthZeroDebugMixin {

    @Inject(method = "growthTickStep", at = @At("RETURN"))
    private static void bd$debugWhyGrowthIsZero(
            Level level, BlockPos pos, BlockState state, RandomSource random,
            long fromTick, long toTick, CropBlockEntity crop,
            CallbackInfoReturnable<Boolean> cir,

            // locals（用 name 抓；若抓不到再改 ordinal 版）
            @Local(name = "tickDelta") long tickDelta,
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
            @Local(name = "growthLimit") float growthLimit,

            @Local(name = "remainingGrowthDelta") float remainingGrowthDelta
    ) {
        if (level.isClientSide()) return;

        // 只在「沒有死掉（true）、而且在 Nether（ultraWarm）、有時間增量」的情況下檢查
        if (!cir.getReturnValue()) return;
        if (!level.dimensionType().ultraWarm()) return;
        if (tickDelta <= 0) return;

        final float growthDelta = growth - initialGrowth;

        // 你要抓的是「長不起來」：growthDelta 幾乎 0
        // 用一個很小的 epsilon 避免浮點誤差
        if (growthDelta > 1e-9f) return;

        // 追加：判斷是不是被 growthLimit 卡住、或 totalGrowthDelta 本身就 <=0
        final boolean limitBlocksGrowth = !(initialGrowth < growthLimit); // 對應原 if 條件的一部分
        final boolean deltaNonPositive = !(totalGrowthDelta > 0.0f);

        // 查一下下面的 farmland BE 是否真的存在（有時候其實不是 SoulFarmland，就會 nutrientsConsumed=0）
        BlockEntity farmlandBe = level.getBlockEntity(pos.below());
        String farmlandInfo = (farmlandBe == null) ? "null" : farmlandBe.getClass().getName();

        ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(state.getBlock());

        /*
        BeneathDelight.LOGGER.warn(
                "[NCropDebug] growth stayed 0 (server) | pos={} block={} | ultraWarm={} growing={} | " +
                        "from={} to={} tickDelta={} | " +
                        "initialGrowth={} growth={} growthLimit={} limitBlocksGrowth={} | " +
                        "totalGrowthDelta={} remainingGrowthDelta={} deltaNonPositive={} | " +
                        "expiry={} localExpiryLimit={} | yield={} | " +
                        "nutrient primary={} avail={} req={} consumed={} | farmlandBE={}",
                pos, blockId,
                level.dimensionType().ultraWarm(), growing,
                fromTick, toTick, tickDelta,
                initialGrowth, growth, growthLimit, limitBlocksGrowth,
                totalGrowthDelta, remainingGrowthDelta, deltaNonPositive,
                expiry, localExpiryLimit,
                actualYield,
                primaryNutrient, nutrientsAvailable, nutrientsRequired, nutrientsConsumed,
                farmlandInfo
        );

         */
    }
}

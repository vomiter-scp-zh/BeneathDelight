package com.vomiter.beneathdelight.mixin.stove;

import com.soytutta.mynethersdelight.common.block.NetherStoveBlock;
import com.soytutta.mynethersdelight.common.block.entity.NetherStoveBlockEntity;
import com.vomiter.survivorsdelight.adapter.HeatSourceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = NetherStoveBlockEntity.class, remap = false)
public abstract class NetherStoveBlockEntity_FuelAndHeat implements HeatSourceBlockEntity {
    public float sdtfc$getTemperature(){
        if(!((BlockEntity)(Object)this).getBlockState().getValue(NetherStoveBlock.LIT)) return 0;
        return 550;
    }
}

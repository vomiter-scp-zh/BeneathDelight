package com.vomiter.beneathdelight.mixin.stove;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;

@Mixin(value = AbstractStoveBlockEntity.class, remap = false)
public interface NetherStoveBlockEntity_Accessor {
    @Accessor("cookingProgress")
    int[] getCookingTimes();

    @Accessor("cookingTime")
    int[] getCookingTimesTotal();
}

package com.vomiter.beneathdelight.mixin.stove;

import com.soytutta.mynethersdelight.common.block.entity.NetherStoveBlockEntity;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = NetherStoveBlockEntity.class, remap = false)
public interface NetherStoveBlockEntity_Accessor {
    @Accessor("cookingTimes")
    int[] getCookingTimes();

    @Accessor("cookingTimesTotal")
    int[] getCookingTimesTotal();

    @Accessor("lastRecipeIDs")
    ResourceLocation[] getLastRecipeIDs();
}

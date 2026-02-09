package com.vomiter.beneathdelight.data.recipe;

import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.block.Blocks;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.function.Consumer;

public class ModCraftingRecipes {
    public void save(Consumer<FinishedRecipe> out){
        misc(out);
    }

    void misc(Consumer<FinishedRecipe> out){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STOVE.get())
                .pattern("NIN")
                .pattern("B B")
                .pattern("BnB")
                .define('B', Blocks.POLISHED_BLACKSTONE_BRICKS)
                .define('I', TFCItems.WROUGHT_IRON_GRILL.get())
                .define('N', Blocks.NETHER_BRICKS)
                .define('n', Blocks.NETHERRACK)
                .unlockedBy("has_firestick", InventoryChangeTrigger.TriggerInstance.hasItems(TFCItems.FIRESTARTER.get()))
                .save(out, SDUtils.RLUtils.build("crafting/misc/nether_stove"));

    }
}

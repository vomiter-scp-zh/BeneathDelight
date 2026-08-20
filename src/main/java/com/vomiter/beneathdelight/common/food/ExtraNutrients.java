package com.vomiter.beneathdelight.common.food;

import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.survivorsdelight.adapter.cooking_pot.dynamic.CookingPotDynamicRules;
import com.vomiter.survivorsdelight.adapter.cooking_pot.dynamic.DynamicFoodContext;
import com.vomiter.survivorsdelight.util.FoodDataBuilder;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.FoodData;
import net.dries007.tfc.common.capabilities.food.IFood;
import net.dries007.tfc.common.capabilities.food.Nutrient;
import net.dries007.tfc.common.items.Food;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Optional;

public class ExtraNutrients {
    public static void onCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            CookingPotDynamicRules.register(
                    new CookingPotDynamicRules.RuleHolder(
                            2000,
                            Helpers.id("stuffed_hoglin"),
                            ((food, context) -> {
                                if (context.phase().equals(DynamicFoodContext.Phase.INDIVIDUAL)){
                                    if (context.stack().is(MNDItems.RAW_STUFFED_HOGLIN.get())){
                                        var stuffedHoglinFood = Optional.ofNullable(FoodCapability.get(context.stack())).map(IFood::getData).orElse(FoodData.EMPTY);
                                        return food.addBuilder(FoodDataBuilder.from(stuffedHoglinFood).mulNutrient(1.6f, Nutrient.PROTEIN).mulNutrient(3, Nutrient.VEGETABLES));
                                    }
                                }
                                return food;
                            })
                    )
            );

            CookingPotDynamicRules.register(
                    new CookingPotDynamicRules.RuleHolder(
                            2000,
                            Helpers.id("ghast_sourdough"),
                            ((food, context) -> {
                                if (context.phase().equals(DynamicFoodContext.Phase.INDIVIDUAL)){
                                    if (context.stack().is(MNDItems.GHAST_SOURDOUGH.get())){
                                        return food.addData(CookingPotDynamicRules.getBuiltInFoodData(Food.WHEAT_BREAD));
                                    }
                                }
                                return food;
                            })
                    )
            );

            CookingPotDynamicRules.register(
                    new CookingPotDynamicRules.RuleHolder(
                            1000,
                            Helpers.id("bullet_pepper"),
                            ((food, context) -> {
                                if (context.phase().equals(DynamicFoodContext.Phase.INDIVIDUAL)){
                                    if (context.stack().is(MNDItems.BULLET_PEPPER.get())){
                                        return food.mulNutrient(1.3f, Nutrient.VEGETABLES, Nutrient.DAIRY, Nutrient.FRUIT);
                                    }
                                }
                                return food;
                            })
                    )
            );

        });
    }
}

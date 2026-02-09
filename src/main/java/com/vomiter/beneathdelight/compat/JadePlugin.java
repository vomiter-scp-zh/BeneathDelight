package com.vomiter.beneathdelight.compat;

import com.soytutta.mynethersdelight.common.block.StuffedHoglinBlock;
import com.vomiter.survivorsdelight.common.food.block.SDDecayingBlockEntity;
import com.vomiter.survivorsdelight.compat.jade.SDJadePlugin;
import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(SDJadePlugin.FDDecayComponentProvider.INSTANCE, StuffedHoglinBlock.class);
    }
}


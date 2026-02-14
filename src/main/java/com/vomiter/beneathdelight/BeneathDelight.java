package com.vomiter.beneathdelight;

import com.mojang.logging.LogUtils;
import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import com.vomiter.beneathdelight.common.event.EventHandler;
import com.vomiter.beneathdelight.common.registry.ModRegistries;
import com.vomiter.beneathdelight.compat.ValidBlockEntityExpansion;
import com.vomiter.beneathdelight.data.ModDataGenerator;
import com.vomiter.survivorsdelight.common.food.block.DecayingFeastBlockEntity;
import com.vomiter.survivorsdelight.common.food.block.SDDecayingBlockEntity;
import com.vomiter.survivorsdelight.mixin.BlockEntityTypeAccessor;
import com.vomiter.survivorsdelight.registry.SDBlockEntityTypes;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.HashSet;

@Mod(BeneathDelight.MOD_ID)
public class BeneathDelight
{
    //DONE: stove compat

    //TODO: decaying
    /*
    * TODO: DECAY BLOCK ENTITY
    *  [X] STUFFED HOGLIN
    *  [X] MAGMA CAKE
    *  [X] BREAD LOAF
    *  [X] STRIDER LOAF
    *  [X] COLD STRIDER LOAF
     */

    //TODO: hell forge heat source for resurgent soil farmland
    //TODO: make soul rich soil support mushroom
    //TODO: brick cabinet
    //TODO: make hoglins drop loin
    //TODO: stuffed hoglin decay
    //TODO: hoglin trophy gold ingot -> brass mechanism
    //TODO: hotdog accept tfc breads
    //TODO: sausage and potatoes accept tfc potato
    //TODO: breakfast sampler accept tfc boiled/fried egg
    //TODO: deviled egg accept tfc boiled egg


    //TODO: Hot cream, make "red/blue steel bucket" able to replace the bucket as container

    public static final String MOD_ID = "beneathdelight";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BeneathDelight(FMLJavaModLoadingContext context) {


        EventHandler.init();
        IEventBus modBus = context.getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(ModDataGenerator::generateData);
        ModRegistries.register(modBus);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ValidBlockEntityExpansion.commonSetup(event);
    }

}

package com.vomiter.beneathdelight.compat;

import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import com.vomiter.beneathdelight.common.registry.ModBlocks;
import com.vomiter.survivorsdelight.common.container.SDCabinetBlockEntity;
import com.vomiter.survivorsdelight.common.food.block.DecayingFeastBlockEntity;
import com.vomiter.survivorsdelight.mixin.BlockEntityTypeAccessor;
import com.vomiter.survivorsdelight.registry.SDBlockEntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashSet;

public class ValidBlockEntityExpansion {
    public static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ValidBlockEntityExpansion::expand);
    }

    static void expand(){
        feastDecay();
        sdCabinet();
    }

    static void feastDecay(){
        BlockEntityType<DecayingFeastBlockEntity> type = SDBlockEntityTypes.FEAST_DECAYING.get();
        BlockEntityTypeAccessor acc = (BlockEntityTypeAccessor)type;
        HashSet<Block> validBlocks = new HashSet<>(acc.getValidBlocks());
        acc.setValidBlocks(validBlocks);
        validBlocks.add(MNDBlocks.COLD_STRIDERLOAF_BLOCK.get());
        validBlocks.add(MNDBlocks.STRIDERLOAF_BLOCK.get());
    }

    static void sdCabinet(){
        BlockEntityType<SDCabinetBlockEntity> type = SDBlockEntityTypes.SD_CABINET.get();
        BlockEntityTypeAccessor acc = (BlockEntityTypeAccessor)type;
        HashSet<Block> validBlocks = new HashSet<>(acc.getValidBlocks());
        acc.setValidBlocks(validBlocks);
        validBlocks.add(ModBlocks.NETHER_BRICKS_CABINET.get());
        validBlocks.add(ModBlocks.RED_NETHER_BRICKS_CABINET.get());
        validBlocks.add(ModBlocks.BLACKSTONE_BRICKS_CABINET.get());
        validBlocks.add(ModBlocks.POWDERY_CABINET.get());
    }
}

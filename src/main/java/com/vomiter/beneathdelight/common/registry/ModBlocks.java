package com.vomiter.beneathdelight.common.registry;

import com.vomiter.survivorsdelight.common.container.SDCabinetBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = ModRegistries.createRegistry(ForgeRegistries.BLOCKS);

    public static final RegistryObject<Block> NETHER_BRICKS_CABINET = BLOCKS.register("nether_bricks_cabinet", () -> new SDCabinetBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));
    public static final RegistryObject<Block> RED_NETHER_BRICKS_CABINET = BLOCKS.register("red_nether_bricks_cabinet", () -> new SDCabinetBlock(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS)));
    public static final RegistryObject<Block> BLACKSTONE_BRICKS_CABINET = BLOCKS.register("blackstone_bricks_cabinet", () -> new SDCabinetBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));
    public static final RegistryObject<Block> POWDERY_CABINET = BLOCKS.register("powdery_cabinet", () -> new SDCabinetBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).sound(SoundType.BAMBOO_WOOD).mapColor(MapColor.TERRACOTTA_GRAY)));

}

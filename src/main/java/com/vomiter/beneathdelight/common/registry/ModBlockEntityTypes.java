package com.vomiter.beneathdelight.common.registry;

import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.common.food.block.DecayingBreadLoafBlockEntity;
import com.vomiter.beneathdelight.common.food.block.DecayingMagmaCakeBlockEntity;
import com.vomiter.beneathdelight.common.food.block.DecayingStuffedHoglinBlockEntity;
import com.vomiter.survivorsdelight.common.food.block.DecayingFeastBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BeneathDelight.MOD_ID);

    public static final RegistryObject<BlockEntityType<DecayingStuffedHoglinBlockEntity>> STUFFED_HOGLIN_DECAYING =
            BLOCK_ENTITIES.register("hoglin_decaying",
                    () -> BlockEntityType.Builder.of(
                            DecayingStuffedHoglinBlockEntity::new,
                            MNDBlocks.STUFFED_HOGLIN.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<DecayingMagmaCakeBlockEntity>> MAGMA_CAKE_DECAYING =
            BLOCK_ENTITIES.register("magma_cake_decaying",
                    () -> BlockEntityType.Builder.of(
                            DecayingMagmaCakeBlockEntity::new,
                            MNDBlocks.MAGMA_CAKE.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<DecayingBreadLoafBlockEntity>> BREAD_LOAF_DECAYING =
            BLOCK_ENTITIES.register("bread_loaf_decaying",
                    () -> BlockEntityType.Builder.of(
                            DecayingBreadLoafBlockEntity::new,
                            MNDBlocks.BREAD_LOAF_BLOCK.get()
                    ).build(null));

}

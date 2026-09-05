package com.vomiter.beneathdelight.mixin.stove;

import com.soytutta.mynethersdelight.common.block.entity.NetherStoveBlockEntity;
import com.vomiter.survivorsdelight.adapter.stove.IStoveBlockEntity;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(value = NetherStoveBlockEntity.class, remap = false)
public abstract class NetherStoveBlockEntity_AccessorImp extends AbstractStoveBlockEntity implements IStoveBlockEntity {

    protected NetherStoveBlockEntity_AccessorImp(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntityType, blockPos, blockState, recipeType);
    }

    public NetherStoveBlockEntity sdtfc$getBlockEntity(){
        return (NetherStoveBlockEntity) (Object)this;
    }
    ;
    public ItemStackHandler sdtfc$getInventory(){
        return getItems();
    };

    @Unique private final HeatingRecipe[] cachedHeatingRecipes = new HeatingRecipe[6];

    @Override
    public HeatingRecipe[] sdtfc$getCachedRecipes() {
        return cachedHeatingRecipes;
    }

    @Override
    public void sdtfc$reduceLeftBurnTick(int i){

    }

}

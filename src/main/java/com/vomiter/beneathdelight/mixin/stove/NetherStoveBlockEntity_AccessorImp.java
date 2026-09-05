package com.vomiter.beneathdelight.mixin.stove;

import com.soytutta.mynethersdelight.common.block.entity.NetherStoveBlockEntity;
import com.vomiter.beneathdelight.adapter.INetherStoveBlockEntity;
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
public abstract class NetherStoveBlockEntity_AccessorImp extends AbstractStoveBlockEntity implements INetherStoveBlockEntity {

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

    public int[] sdtfc$getCookingTimes(){
        if(this instanceof NetherStoveBlockEntity_Accessor acc){
            return acc.getCookingTimes();
        }
        return null;
    };
    public int[] sdtfc$getCookingTimesTotal(){
        if(this instanceof NetherStoveBlockEntity_Accessor acc){
            return acc.getCookingTimesTotal();
        }
        return null;
    };
}

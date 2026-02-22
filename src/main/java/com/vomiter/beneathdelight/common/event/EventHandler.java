package com.vomiter.beneathdelight.common.event;

import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.vomiter.beneathdelight.common.command.ModCommand;
import com.vomiter.survivorsdelight.common.food.block.SDDecayingBlockEntity;
import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.items.Food;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.atomic.AtomicInteger;

public class EventHandler {
    public static void init(){
        final IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(EventHandler::onRegisterCommands);
        bus.addListener(EventHandler::onDrop);
        //bus.addListener(EventHandler::onDebugRightClick);
        bus.addListener(DelayedStriderLoafTask::onServerTickEnd);
    }

    //DEBUG
    public static void onDebugRightClick(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        BlockPos pos = event.getHitVec().getBlockPos();
        BlockState state = player.level().getBlockState(pos);
        String blockId = ForgeRegistries.BLOCKS.getKey(state.getBlock()).toString();
        player.sendSystemMessage(Component.literal(blockId));
        if(player.level().getBlockEntity(pos) instanceof SDDecayingBlockEntity decay){
            player.sendSystemMessage(Component.literal("is rotten on" + (player.level().isClientSide? "CLIENT": "SERVER") + ":" + decay.isRotten()));
            player.sendSystemMessage(Component.literal("The stored stack is " + decay.getStack().getDescriptionId()));
        }
    }

    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommand.register(event.getDispatcher());
    }

    public static void onDrop(LivingDropsEvent event){
        if(event.getEntity().level().isClientSide) return;
        if(!(event.getEntity() instanceof Hoglin)) return;
        AtomicInteger porkCount = new AtomicInteger();
        event.getDrops().forEach(ie -> {
            if(ie.getItem().is(SDUtils.getTFCFoodItem(Food.PORK))){
                porkCount.addAndGet(ie.getItem().getCount());
                ie.discard();
            }
        });
        Level level = event.getEntity().level();
        level.addFreshEntity(new ItemEntity(
                level,
                event.getEntity().getX(),
                event.getEntity().getY(),
                event.getEntity().getZ(),
                new ItemStack(MNDItems.HOGLIN_LOIN.get(), porkCount.get())
        ));
    }


}

package com.vomiter.beneathdelight.common.util;

import com.vomiter.survivorsdelight.common.food.block.DecayingFeastBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayDeque;

public final class DelayedStriderLoafTask {
    private DelayedStriderLoafTask() {}

    private static final class StriderLoafTask {
        final ServerLevel level;
        final BlockPos pos;
        final Block expectedBlock; // optional: 用來防呆避免已被換掉
        int ticks;

        StriderLoafTask(ServerLevel level, BlockPos pos, Block expectedBlock, int ticks) {
            this.level = level;
            this.pos = pos.immutable();
            this.expectedBlock = expectedBlock;
            this.ticks = ticks;
        }
    }

    private static final ArrayDeque<StriderLoafTask> QUEUE = new ArrayDeque<>();

    public static void scheduleStriderLoafTask(ServerLevel level, BlockPos pos, ItemStack stack, Block expectedBlock, int delayTicks) {
        QUEUE.add(new StriderLoafTask(level, pos, expectedBlock, delayTicks));
    }

    public static void onServerTickEnd(TickEvent.ServerTickEvent event) {
        int n = QUEUE.size();
        for (int i = 0; i < n; i++) {
            StriderLoafTask t = QUEUE.pollFirst();
            if (t == null) break;

            if (--t.ticks > 0) {
                QUEUE.addLast(t);
                continue;
            }

            if (t.level.isClientSide) continue;
            if (!t.level.isLoaded(t.pos)) continue;
            BlockState state = t.level.getBlockState(t.pos);
            if (t.expectedBlock != null && state.getBlock() != t.expectedBlock) {
                continue;
            }
            if (t.level.getBlockEntity(t.pos) instanceof DecayingFeastBlockEntity decay) {
                decay.sendVanillaUpdatePacket();
            }
        }
    }
}

package com.vomiter.beneathdelight.common.event;

import com.vomiter.beneathdelight.registry.ModBlocks;
import com.vomiter.beneathdelight.registry.ModItems;
import com.vomiter.survivorsdelight.registry.SDCreativeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class CreativeTabEvents {

    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == SDCreativeTab.MAIN.getKey()) {
            event.accept(ModBlocks.CRIMSON_CABINET);
            event.accept(ModBlocks.WARPED_CABINET);
            event.accept(ModBlocks.POWDERY_CABINET);
            ModItems.ITEMS.getEntries().forEach(obj -> event.accept(obj.get()));
        }
    }
}
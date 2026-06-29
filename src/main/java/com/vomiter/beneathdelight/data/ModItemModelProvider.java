package com.vomiter.beneathdelight.data;

import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BeneathDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModItems.ITEMS.getEntries().forEach(obj -> generatedItem(obj.getId().getPath()));
    }

    private void generatedItem(String path) {
        trackTexture(path);
        singleTexture(
                "item/" + path,
                mcLoc("item/generated"),
                "layer0",
                modLoc("item/" + path)
        );
    }

    private void handheldItem(String path) {
        trackTexture(path);
        singleTexture(
                "item/" + path,
                mcLoc("item/handheld"),
                "layer0",
                modLoc("item/" + path)
        );
    }

    private void trackTexture(String pathNoExt) {
        existingFileHelper.trackGenerated(
                modLoc("item/" + pathNoExt),
                PackType.CLIENT_RESOURCES,
                ".png",
                "textures"
        );
    }

}
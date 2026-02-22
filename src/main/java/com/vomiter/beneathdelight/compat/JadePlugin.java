package com.vomiter.beneathdelight.compat;

import com.soytutta.mynethersdelight.common.block.StuffedHoglinBlock;
import com.vomiter.survivorsdelight.compat.jade.SDJadePlugin;
import snownee.jade.api.*;

@SuppressWarnings("unused")
@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(SDJadePlugin.FDDecayComponentProvider.INSTANCE, StuffedHoglinBlock.class);
    }
}


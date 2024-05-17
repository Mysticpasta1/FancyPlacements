package com.mystic.fancyplacements;

import com.mystic.fancyplacements.init.Registry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "fancyplacements", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void initClient(FMLClientSetupEvent event) {
        registerDummyTile(Registry.DUMMY_TILE.get());
    }

    private static void registerDummyTile(BlockEntityType<DummyTileEntity> registryObject) {
        BlockEntityRenderers.register(registryObject, DummyTileRenderer::new);
    }
}

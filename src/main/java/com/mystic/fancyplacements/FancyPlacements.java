package com.mystic.fancyplacements;

import com.mystic.fancyplacements.init.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod("fancyplacements")
public class FancyPlacements {
    public FancyPlacements() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Registry.init(bus);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::onPlacement);
    }

    public void onPlacement(BlockEvent.EntityPlaceEvent event) {
        BlockState block = event.getPlacedBlock();
        LevelAccessor level = event.getLevel();
        if (!((Level) level).isClientSide && !block.is(Registry.DUMMY_BLOCK.get())) {
            //TODO modded blocks!
            if(block.getBlock() instanceof TallFlowerBlock || block.getBlock() instanceof BedBlock || block.getBlock() instanceof DoorBlock
            || block.getBlock() instanceof TallGrassBlock || block.getBlock() instanceof TallSeagrassBlock) {
                return;
            }
            ServerLifecycleHooks.getCurrentServer().submit(() -> {
                event.setCanceled(true);
                level.setBlock(event.getPos(), Registry.DUMMY_BLOCK.get().defaultBlockState(), 11);
                if (level.getBlockEntity(event.getPos()) instanceof DummyBlockEntity dummy) {
                    dummy.target = block;
                }
            });
        }
    }
}

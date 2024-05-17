package com.mystic.fancyplacements.init;

import com.mystic.fancyplacements.DummyPlacementBlock;
import com.mystic.fancyplacements.DummyTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "fancyplacements");
    public static final DeferredRegister<BlockEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "fancyplacements");
    public static final RegistryObject<Block> DUMMY_BLOCK = BLOCKS.register("dummy", DummyPlacementBlock::new);
    public static final RegistryObject<BlockEntityType<DummyTileEntity>> DUMMY_TILE = TILE_TYPES.register("dummy",
            () -> BlockEntityType.Builder.of(DummyTileEntity::new, DUMMY_BLOCK.get()).build(null));

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
        TILE_TYPES.register(bus);
    }
}

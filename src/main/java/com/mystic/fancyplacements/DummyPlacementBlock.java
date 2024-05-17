package com.mystic.fancyplacements;

import com.mystic.fancyplacements.init.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DummyPlacementBlock extends Block implements EntityBlock {
    public DummyPlacementBlock() {
        super(Properties.of(Material.AIR).noOcclusion());
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos p_153215_, @NotNull BlockState p_153216_) {
        return new DummyTileEntity(p_153215_, p_153216_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> tBlockEntityType) {
        return (level1, blockPos, blockState, tBlockEntity) -> DummyTileEntity.tick(level1, blockPos, blockState, new DummyTileEntity(blockPos, blockState));
    }
}

package com.mystic.fancyplacements;

import com.mystic.fancyplacements.init.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class DummyTileEntity extends BlockEntity {

    public int age = 10;
    public BlockState blockState1;

    public DummyTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        this(p_155229_, p_155230_, Blocks.DIORITE.defaultBlockState());
    }

    public DummyTileEntity(BlockPos p_155229_, BlockState p_155230_, BlockState blockState2) {
        super(Registry.DUMMY_TILE.get(), p_155229_, p_155230_);
        blockState1 = blockState2;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, DummyTileEntity tile) {
        if(!pLevel.isClientSide) {
            tile.age += 1;
            if (tile.age >= 60) {
                pLevel.setBlock(pPos, tile.blockState1, 3);
            }
            tile.sync();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("age", age);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.age = pTag.getInt("age");
    }

    @Override
    public CompoundTag getUpdateTag() {
        var compound = new CompoundTag();
        compound.putInt("age", age);
        saveWithFullMetadata();
        return compound;
    }

    public void sync() {
        setChanged();
        if(getLevel() != null)
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
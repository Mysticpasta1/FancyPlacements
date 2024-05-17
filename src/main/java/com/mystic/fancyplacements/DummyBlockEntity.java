package com.mystic.fancyplacements;

import com.mystic.fancyplacements.init.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

public class DummyBlockEntity extends BlockEntity {

    public int age = 0;
    public BlockState target;

    public DummyBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        this(p_155229_, p_155230_, Blocks.DIORITE.defaultBlockState());
    }

    public DummyBlockEntity(BlockPos p_155229_, BlockState p_155230_, BlockState target) {
        super(Registry.DUMMY_TILE.get(), p_155229_, p_155230_);
        this.target = target;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, DummyBlockEntity entity) {
        if(!pLevel.isClientSide) {
            ++entity.age;
            if (entity.age >= 10) {
                pLevel.setBlock(pPos, entity.target, 3);
            }
            entity.sync();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("age", age);
        pTag.put("target", NbtOps.INSTANCE.withEncoder(BlockState.CODEC).andThen(a -> a.result()).andThen(Optional::orElseThrow).apply(target));
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.age = pTag.getInt("age");
        this.target = NbtOps.INSTANCE.withDecoder(BlockState.CODEC).andThen(a -> a.result()).andThen(Optional::orElseThrow).apply(pTag.getCompound("target")).getFirst();
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
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
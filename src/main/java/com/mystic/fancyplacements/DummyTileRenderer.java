package com.mystic.fancyplacements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class DummyTileRenderer implements BlockEntityRenderer<DummyTileEntity> {

    public DummyTileRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(DummyTileEntity tile, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                       int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);

        float scale = tile.age / 60f;
        poseStack.scale(scale, scale, scale);

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(tile.blockState1, poseStack, buffer, light, overlay);
        poseStack.popPose();
    }


}
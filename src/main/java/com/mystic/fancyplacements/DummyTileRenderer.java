package com.mystic.fancyplacements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class DummyTileRenderer implements BlockEntityRenderer<DummyBlockEntity> {

    public DummyTileRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(DummyBlockEntity tile, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                       int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);

        float scale = tile.age / 10f;
        poseStack.scale(scale, scale, scale);

        poseStack.translate(-0.5, -0.5, -0.5);



        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(tile.target, poseStack, buffer, light, overlay);
        poseStack.popPose();
    }


}
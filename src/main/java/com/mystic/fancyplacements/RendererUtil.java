package com.mystic.fancyplacements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;


public class RendererUtil {
    public static void renderDummy(VertexConsumer builder, PoseStack matrixStackIn, float w,
                                    TextureAtlasSprite sprite, int combinedLightIn,
                                    boolean flippedY, BlockPos pos, Level level, float partialTicks) {
        int lu = combinedLightIn & '\uffff';
        int lv = combinedLightIn >> 16 & '\uffff';
        float atlasScaleU = sprite.getU1() - sprite.getU0();
        float atlasScaleV = sprite.getV1() - sprite.getV0();
        float minU = sprite.getU0();
        float minV = sprite.getV0();
        float maxU = minU + atlasScaleU * w;
        float maxV = minV + atlasScaleV * w;
        float maxV2 = minV + atlasScaleV * w;

        long t = level == null ? System.currentTimeMillis() / 50 : level.getGameTime();
        float time = ((float) Math.floorMod((long) (pos.getX() * 7 + pos.getY() * 9 + pos.getZ() * 13) + t, 100L) + partialTicks) / 100.0F;


        //long time = System.currentTimeMillis();
        float unw = Mth.cos(((float) Math.PI * 2F) * (time + 0));
        float une = Mth.cos(((float) Math.PI * 2F) * (time + 0.25f));
        float use = Mth.cos(((float) Math.PI * 2F) * (time + 0.5f));
        float usw = Mth.cos(((float) Math.PI * 2F) * (time + 0.75f));

        float dnw = use;
        float dne = usw;
        float dse = unw;
        float dsw = une;

        float l = w / 2f;

        //addQuadTop(builder, matrixStackIn, -l+dx1, w, l, l, w, -l, minU, minV, maxU, maxV2, r, g, b, a, lu, lv, 0, 1, 0);
        //top
        addVert(builder, matrixStackIn, -l - usw, l + usw, l + usw, minU, maxV2, lu, lv, 0, 1, 0);
        addVert(builder, matrixStackIn, l + use, l + use, l + use, maxU, maxV2, lu, lv, 0, 1, 0);
        addVert(builder, matrixStackIn, l + une, l + une, -l - une, maxU, minV, lu, lv, 0, 1, 0);
        addVert(builder, matrixStackIn, -l - unw, l + unw, -l - unw, minU, minV, lu, lv, 0, 1, 0);


        //addQuadTop(builder, matrixStackIn, -l, 0, -l, l, 0, l, minU, minV, maxU, maxV2, r5, g5, b5, a, lu, lv, 0, -1, 0);

        addVert(builder, matrixStackIn, -l - dnw, -l - dnw, -l - dnw, minU, maxV2, lu, lv, 0, -1, 0);
        addVert(builder, matrixStackIn, l + dne, -l - dne, -l - dne, maxU, maxV2, lu, lv, 0, -1, 0);
        addVert(builder, matrixStackIn, l + dse, -l - dse, l + dse, maxU, minV, lu, lv, 0, -1, 0);
        addVert(builder, matrixStackIn, -l - dsw, -l - dsw, l + dsw, minU, minV, lu, lv, 0, -1, 0);

        if (flippedY) {
            float temp = minV;
            minV = maxV;
            maxV = temp;
        }

        // north z-
        // x y z u v r g b a lu lv
        //addQuadSide(builder, matrixStackIn, l, 0, -l, -l, w, -l, minU, minV, maxU, maxV, r8, g8, b8, a, lu, lv, 0, 0, 1);
        addVert(builder, matrixStackIn, l + dne, -l - dne, -l - dne, minU, maxV, lu, lv, 0, 0, 1);
        addVert(builder, matrixStackIn, -l - dnw, -l - dnw, -l - dnw, maxU, maxV, lu, lv, 0, 0, 1);
        addVert(builder, matrixStackIn, -l - unw, l + unw, -l - unw, maxU, minV, lu, lv, 0, 0, 1);
        addVert(builder, matrixStackIn, l + une, l + une, -l - une, minU, minV, lu, lv, 0, 0, 1);
        // west
        //addQuadSide(builder, matrixStackIn, -l, 0, -l, -l, w, l, minU, minV, maxU, maxV, r6, g6, b6, a, lu, lv, -1, 0, 0);
        addVert(builder, matrixStackIn, -l - dnw, -l - dnw, -l - dnw, minU, maxV, lu, lv, -1, 0, 0);
        addVert(builder, matrixStackIn, -l - dsw, -l - dsw, l + dsw, maxU, maxV, lu, lv, -1, 0, 0);
        addVert(builder, matrixStackIn, -l - usw, l + usw, l + usw, maxU, minV, lu, lv, -1, 0, 0);
        addVert(builder, matrixStackIn, -l - unw, l + unw, -l - unw, minU, minV, lu, lv, -1, 0, 0);
        // south
        //addQuadSide(builder, matrixStackIn, -l, 0, l, l, w, l, minU, minV, maxU, maxV, r8, g8, b8, a, lu, lv, 0, 0, -1);
        addVert(builder, matrixStackIn, -l - dsw, -l - dsw, l + dsw, minU, maxV, lu, lv, 0, 0, -1);
        addVert(builder, matrixStackIn, l + dse, -l - dse, l + dse, maxU, maxV, lu, lv, 0, 0, -1);
        addVert(builder, matrixStackIn, l + use, l + use, l + use, maxU, minV, lu, lv, 0, 0, -1);
        addVert(builder, matrixStackIn, -l - usw, l + usw, l + usw, minU, minV, lu, lv, 0, 0, -1);
        // east
        //addQuadSide(builder, matrixStackIn, l, 0, l, l, w, -l, minU, minV, maxU, maxV, r6, g6, b6, a, lu, lv, 1, 0, 0);
        addVert(builder, matrixStackIn, l + dse, -l - dse, l + dse, minU, maxV, lu, lv, 1, 0, 0);
        addVert(builder, matrixStackIn, l + dne, -l - dne, -l - dne, maxU, maxV, lu, lv, 1, 0, 0);
        addVert(builder, matrixStackIn, l + une, l + une, -l - une, maxU, minV, lu, lv, 1, 0, 0);
        addVert(builder, matrixStackIn, l + use, l + use, l + use, minU, minV, lu, lv, 1, 0, 0);
    }


    public static void addVert(VertexConsumer builder, PoseStack matrixStackIn, float x, float y, float z, float u, float v, int lu, int lv, float nx, float ny, float nz) {
        builder.vertex(matrixStackIn.last().pose(), x, y, z).color(1,1,1,1).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lu, lv)
                .normal(matrixStackIn.last().normal(), nx, ny, nz).endVertex();
    }
}
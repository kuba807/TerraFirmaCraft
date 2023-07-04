/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import net.dries007.tfc.common.blockentities.GrillBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;

import static net.dries007.tfc.common.blockentities.GrillBlockEntity.*;

public class GrillBlockEntityRenderer implements BlockEntityRenderer<GrillBlockEntity>
{
    @Override
    public void render(GrillBlockEntity grill, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay)
    {
        grill.getCapability(Capabilities.ITEM).ifPresent(cap -> {
            for (int i = SLOT_EXTRA_INPUT_START; i <= SLOT_EXTRA_INPUT_END; i++)
            {
                ItemStack item = cap.getStackInSlot(i);
                if (!item.isEmpty())
                {
                    float yOffset = 0.625f;
                    poseStack.pushPose();
                    poseStack.translate(0.3, 0.003125D + yOffset, 0.28);
                    poseStack.scale(0.3f, 0.3f, 0.3f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(90F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(180F));

                    float translateAmount = -1.4F;
                    int ordinal = i - SLOT_EXTRA_INPUT_START;
                    if (ordinal == 1 || ordinal == 3)
                    {
                        poseStack.translate(translateAmount, 0, 0);
                    }
                    if (ordinal == 2 || ordinal == 3)
                    {
                        poseStack.translate(0, translateAmount, 0);
                    }
                    if (ordinal == 4)
                    {
                        poseStack.translate(translateAmount / 2, translateAmount / 2, 0);
                    }

                    Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, buffer, grill.getLevel(), 0);
                    poseStack.popPose();
                }
            }
        });
    }
}

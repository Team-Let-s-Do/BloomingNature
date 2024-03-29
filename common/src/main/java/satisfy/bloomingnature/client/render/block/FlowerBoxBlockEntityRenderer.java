package satisfy.bloomingnature.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import satisfy.bloomingnature.blocks.FlowerBoxBlock;
import satisfy.bloomingnature.entity.FlowerBoxBlockEntity;

import static satisfy.bloomingnature.util.GeneralUtil.renderBlock;


public class FlowerBoxBlockEntityRenderer implements BlockEntityRenderer<FlowerBoxBlockEntity> {

    public FlowerBoxBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(FlowerBoxBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!entity.hasLevel()) {
            return;
        }
        BlockState selfState = entity.getBlockState();
        if (selfState.getBlock() instanceof FlowerBoxBlock) {
            matrices.pushPose();
            applyBlockAngle(matrices, selfState);
            matrices.translate(-0.25f, 0.25f, 0.25f);
            if (!entity.isSlotEmpty(0)) {
                BlockState state = ((BlockItem) entity.getFlower(0).getItem()).getBlock().defaultBlockState();
                renderBlock(state, matrices, vertexConsumers, entity);
            }
            matrices.translate(0.5f, 0f, 0f);
            if (!entity.isSlotEmpty(1)) {
                BlockState state = ((BlockItem) entity.getFlower(1).getItem()).getBlock().defaultBlockState();
                renderBlock(state, matrices, vertexConsumers, entity);
            }
            matrices.popPose();
        }
    }

    public static void applyBlockAngle(PoseStack matrices, BlockState state) {
        switch (state.getValue(FlowerBoxBlock.FACING)) {
            case EAST -> {
                matrices.translate(-0.5f, 0f, 1f);
                matrices.mulPose(Axis.YP.rotationDegrees(90));
            }
            case SOUTH -> {
                matrices.translate(1f, 0f, 1f);
                matrices.mulPose(Axis.YP.rotationDegrees(180));
            }
            case WEST -> {
                matrices.translate(1.5f, 0f, 0f);
                matrices.mulPose(Axis.YP.rotationDegrees(270));
            }
        }
    }


}
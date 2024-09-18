package bagu_chan.wild_delight.client.render;

import bagu_chan.wild_delight.WildDelight;
import bagu_chan.wild_delight.client.ModModelLayers;
import bagu_chan.wild_delight.client.model.ButterflyModel;
import bagu_chan.wild_delight.entity.ButterFly;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ButterflyRenderer<T extends ButterFly> extends MobRenderer<T, ButterflyModel<T>> {
    private static final ResourceLocation BUTTERFLY = new ResourceLocation(WildDelight.MODID, "textures/entity/butterfly.png");

    public ButterflyRenderer(EntityRendererProvider.Context p_174354_) {
        super(p_174354_, new ButterflyModel<>(p_174354_.bakeLayer(ModModelLayers.BUTTER_FLY)), 0.2F);
    }

    @Override
    protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
        super.scale(p_115314_, p_115315_, p_115316_);
        p_115315_.scale(p_115314_.getScale(), p_115314_.getScale(), p_115314_.getScale());
    }

    public ResourceLocation getTextureLocation(T p_115720_) {
        return BUTTERFLY;
    }
}

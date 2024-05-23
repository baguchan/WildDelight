package bagu_chan.wild_delight.client.render;

import bagu_chan.wild_delight.client.ModModelLayers;
import bagu_chan.wild_delight.client.model.WildChefModel;
import bagu_chan.wild_delight.entity.WildChef;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Pillager;

public class WildChefRenderer<T extends WildChef> extends IllagerRenderer<T> {
    private static final ResourceLocation PILLAGER = new ResourceLocation("textures/entity/illager/pillager.png");

    public WildChefRenderer(EntityRendererProvider.Context p_174354_) {
        super(p_174354_, new WildChefModel<>(p_174354_.bakeLayer(ModModelLayers.WILD_CHEF)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, p_174354_.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(T p_115720_) {
        return PILLAGER;
    }
}

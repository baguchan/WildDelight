package bagu_chan.wild_delight.client;

import bagu_chan.wild_delight.WildDelight;
import bagu_chan.wild_delight.client.render.WildChefRenderer;
import bagu_chan.wild_delight.entity.WildChef;
import bagu_chan.wild_delight.registry.ModEntityTypes;
import net.minecraft.client.model.IllagerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WildDelight.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.WILD_CHEF.get(), WildChefRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.WILD_CHEF, IllagerModel::createBodyLayer);
    }
}

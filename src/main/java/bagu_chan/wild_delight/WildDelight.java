package bagu_chan.wild_delight;

import bagu_chan.wild_delight.registry.ModCreativeTabs;
import bagu_chan.wild_delight.registry.ModEntityTypes;
import bagu_chan.wild_delight.registry.ModItems;
import net.minecraft.world.entity.raid.Raid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WildDelight.MODID)
public class WildDelight
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "wild_delight";
    // Directly reference a slf4j logger
    public WildDelight()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModItems.ITEM_REGISTRY.register(modEventBus);
        ModEntityTypes.ENTITIES.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        Raid.RaiderType.create("wild_chef", ModEntityTypes.WILD_CHEF.get(), new int[]{0, 0, 1, 1, 2, 2, 3, 3});
    }
}

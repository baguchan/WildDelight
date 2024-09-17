package bagu_chan.wild_delight;

import bagu_chan.wild_delight.registry.ModBlocks;
import bagu_chan.wild_delight.registry.ModCreativeTabs;
import bagu_chan.wild_delight.registry.ModEntityTypes;
import bagu_chan.wild_delight.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.raid.Raid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;

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
        ModBlocks.FARMERS_BLOCK_REGISTRY.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.MINECRAFT_ITEM_REGISTRY.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntityTypes.ENTITIES.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(WildDelight.MODID, name.toLowerCase(Locale.ROOT));
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        Raid.RaiderType.create("wild_chef", ModEntityTypes.WILD_CHEF.get(), new int[]{0, 0, 1, 1, 2, 2, 3, 3});
    }

}

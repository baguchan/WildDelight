package bagu_chan.wild_delight.data;

import bagu_chan.wild_delight.WildDelight;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = WildDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.getGenerator().addProvider(event.includeClient(), new BlockstateGenerator(packOutput, event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeClient(), new ItemModelGenerator(packOutput, event.getExistingFileHelper()));
        /*BlockTagsProvider blocktags = new BlockTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeServer(), blocktags);
        event.getGenerator().addProvider(event.includeServer(), new ItemTagGenerator(packOutput, lookupProvider, blocktags.contentsGetter(), event.getExistingFileHelper()));
        */event.getGenerator().addProvider(event.includeServer(), new EntityTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));
      }
}
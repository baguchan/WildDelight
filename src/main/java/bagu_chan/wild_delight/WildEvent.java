package bagu_chan.wild_delight;

import bagu_chan.wild_delight.block.RichSoilFarmlandRevampedBlock;
import bagu_chan.wild_delight.registry.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WildDelight.MODID)
public class WildEvent {
    @SubscribeEvent
    public static void bonemealEvent(BonemealEvent event) {
        if (event.getBlock().is(Blocks.FARMLAND)) {
            event.getLevel().setBlock(event.getPos(), ModBlocks.RICH_SOIL.get().defaultBlockState(), 3);
            event.setResult(Event.Result.ALLOW);
        }

        if (event.getBlock().is(ModBlocks.RICH_SOIL_FARMLAND.get())) {
            int fertilize = event.getBlock().getValue(RichSoilFarmlandRevampedBlock.FERTILIZE);
            if (fertilize < 8) {
                event.getLevel().setBlock(event.getPos(), event.getBlock().setValue(RichSoilFarmlandRevampedBlock.FERTILIZE, fertilize + 1), 3);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}

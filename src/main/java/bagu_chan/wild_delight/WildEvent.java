package bagu_chan.wild_delight;

import bagu_chan.wild_delight.block.RichSoilFarmlandRevampedBlock;
import bagu_chan.wild_delight.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WildDelight.MODID)
public class WildEvent {
    @SubscribeEvent
    public static void bonemealEvent(BonemealEvent event) {
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        if (event.getBlock().is(Blocks.FARMLAND)) {
            level.setBlock(pos, ModBlocks.RICH_SOIL.get().defaultBlockState(), 3);
            event.setResult(Event.Result.ALLOW);
        }

        if (event.getBlock().is(ModBlocks.RICH_SOIL_FARMLAND.get()) && WildConfig.COMMON.richSoilTurnToFarmland.get()) {
            int fertilize = event.getBlock().getValue(RichSoilFarmlandRevampedBlock.FERTILIZE);
            if (fertilize < 8) {
                level.setBlock(pos, event.getBlock().setValue(RichSoilFarmlandRevampedBlock.FERTILIZE, fertilize + 1), 3);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}

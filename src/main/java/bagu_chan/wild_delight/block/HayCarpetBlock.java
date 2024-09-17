package bagu_chan.wild_delight.block;

import bagu_chan.wild_delight.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HayCarpetBlock extends CarpetBlock {
    public HayCarpetBlock(Properties p_152915_) {
        super(p_152915_);
    }

    @Override
    public void entityInside(BlockState p_60495_, Level p_60496_, BlockPos p_60497_, Entity p_60498_) {
        super.entityInside(p_60495_, p_60496_, p_60497_, p_60498_);
        if (p_60498_ instanceof Animal animal) {
            if (animal.getRandom().nextInt(400) == 0) {
                animal.playSound(SoundEvents.CHICKEN_EGG, 1.0F, 1.25F);
                p_60496_.setBlock(p_60497_, ModBlocks.DIRTY_HAY_CARPET.get().defaultBlockState(), 3);
            }
        }
    }

}

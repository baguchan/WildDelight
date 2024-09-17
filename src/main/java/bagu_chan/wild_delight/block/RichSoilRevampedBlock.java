package bagu_chan.wild_delight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.ForgeHooks;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.block.RichSoilBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class RichSoilRevampedBlock extends RichSoilBlock {
    public static final IntegerProperty FERTILIZE = IntegerProperty.create("fertilize", 1, 4);

    public RichSoilRevampedBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FERTILIZE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_53283_) {
        p_53283_.add(FERTILIZE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (!level.isClientSide) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            Block aboveBlock = aboveState.getBlock();
            if (aboveState.is(ModTags.UNAFFECTED_BY_RICH_SOIL) || aboveBlock instanceof TallFlowerBlock) {
                return;
            }

            if (aboveBlock == Blocks.BROWN_MUSHROOM) {
                level.setBlockAndUpdate(pos.above(), ModBlocks.BROWN_MUSHROOM_COLONY.get().defaultBlockState());
                this.decrease(level, state, pos);
                return;
            }

            if (aboveBlock == Blocks.RED_MUSHROOM) {
                level.setBlockAndUpdate(pos.above(), ModBlocks.RED_MUSHROOM_COLONY.get().defaultBlockState());
                this.decrease(level, state, pos);
                return;
            }

            if (Configuration.RICH_SOIL_BOOST_CHANCE.get() == 0.0) {
                return;
            }

            if (aboveBlock instanceof BonemealableBlock growable) {
                if ((double) MathUtils.RAND.nextFloat() <= Configuration.RICH_SOIL_BOOST_CHANCE.get() && growable.isValidBonemealTarget(level, pos.above(), aboveState, false) && ForgeHooks.onCropsGrowPre(level, pos.above(), aboveState, true)) {
                    growable.performBonemeal(level, level.random, pos.above(), aboveState);
                    level.levelEvent(2005, pos.above(), 0);
                    this.decrease(level, state, pos);
                    ForgeHooks.onCropsGrowPost(level, pos.above(), aboveState);
                }
            }
        }

    }

    private void decrease(ServerLevel level, BlockState state, BlockPos pos) {
        int fertilize = state.getValue(FERTILIZE);
        if (fertilize <= 1) {
            level.setBlockAndUpdate(pos, state.setValue(FERTILIZE, fertilize));
        } else {
            level.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState());
        }
    }
}

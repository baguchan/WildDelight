package bagu_chan.wild_delight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.ForgeHooks;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

import java.util.Iterator;

public class RichSoilFarmlandRevampedBlock extends RichSoilFarmlandBlock {
    public static final IntegerProperty FERTILIZE = IntegerProperty.create("fertilize", 1, 4);

    public RichSoilFarmlandRevampedBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0).setValue(FERTILIZE, 1));

    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_53283_) {
        p_53283_.add(MOISTURE, FERTILIZE);
    }

    private static boolean hasWater(LevelReader level, BlockPos pos) {
        Iterator var2 = BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4)).iterator();

        BlockPos nearbyPos;
        do {
            if (!var2.hasNext()) {
                return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
            }

            nearbyPos = (BlockPos) var2.next();
        } while (!level.getFluidState(nearbyPos).is(FluidTags.WATER));

        return true;
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.is(ModBlocks.RICH_SOIL_FARMLAND.get())) {
            return state.getValue(MOISTURE) > 0;
        } else {
            return false;
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

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int moisture = state.getValue(MOISTURE);
        if (!hasWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (moisture > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            }
        } else if (moisture < 7) {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        } else if (moisture == 7) {
            if (Configuration.RICH_SOIL_BOOST_CHANCE.get() == 0.0) {
                return;
            }

            BlockState aboveState = level.getBlockState(pos.above());
            Block aboveBlock = aboveState.getBlock();
            if (aboveState.is(ModTags.UNAFFECTED_BY_RICH_SOIL) || aboveBlock instanceof TallFlowerBlock) {
                return;
            }

            if (aboveBlock instanceof BonemealableBlock growable) {
                if ((double) MathUtils.RAND.nextFloat() <= Configuration.RICH_SOIL_BOOST_CHANCE.get() && growable.isValidBonemealTarget(level, pos.above(), aboveState, false) && ForgeHooks.onCropsGrowPre(level, pos.above(), aboveState, true)) {
                    growable.performBonemeal(level, level.random, pos.above(), aboveState);
                    if (!level.isClientSide) {
                        level.levelEvent(2005, pos.above(), 0);
                    }
                    this.decrease(level, state, pos);
                    ForgeHooks.onCropsGrowPost(level, pos.above(), aboveState);
                }
            }
        }

    }
}

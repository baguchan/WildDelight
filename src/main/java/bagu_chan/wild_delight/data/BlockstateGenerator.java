package bagu_chan.wild_delight.data;

import bagu_chan.wild_delight.WildDelight;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockstateGenerator extends BlockStateProvider {
    public BlockstateGenerator(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, WildDelight.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}

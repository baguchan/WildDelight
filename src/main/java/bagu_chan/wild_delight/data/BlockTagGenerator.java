package bagu_chan.wild_delight.data;

import bagu_chan.wild_delight.WildDelight;
import bagu_chan.wild_delight.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, ExistingFileHelper exFileHelper) {
        super(p_256095_, p_256572_, WildDelight.MODID, exFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider p_255894_) {
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.HAY_CARPET.get()).add(ModBlocks.DIRTY_HAY_CARPET.get());
    }
}

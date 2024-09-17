package bagu_chan.wild_delight.data;

import bagu_chan.wild_delight.WildDelight;
import bagu_chan.wild_delight.registry.ModBlocks;
import bagu_chan.wild_delight.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static bagu_chan.wild_delight.WildDelight.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, WildDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(ModItems.FERTILIZER_BASE);
        this.basicItem(ModItems.FERTILIZER);
        this.toBlock(ModBlocks.DIRTY_HAY_CARPET);
        this.toBlock(ModBlocks.HAY_CARPET);
        egg(ModItems.WILD_CHEF_SPAWNEGG);
    }

    public ItemModelBuilder basicItem(Supplier<Item> item) {
        return this.basicItem(item.get());
    }

    private ItemModelBuilder toBlock(Supplier<? extends Block> b) {
        return toBlockModel(b, BuiltInRegistries.BLOCK.getKey(b.get()).getPath());
    }

    private ItemModelBuilder toBlockModel(Supplier<? extends Block> b, String model) {
        return toBlockModel(b, prefix("block/" + model));
    }

    private ItemModelBuilder toBlockModel(Supplier<? extends Block> b, ResourceLocation model) {
        return withExistingParent(BuiltInRegistries.BLOCK.getKey(b.get()).getPath(), model);
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block) {
        return itemBlockFlat(block, blockName(block));
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String name) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + name));
    }

    public String blockName(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }



    public ItemModelBuilder egg(Supplier<Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
    }
}

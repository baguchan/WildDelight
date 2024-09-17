package bagu_chan.wild_delight.data;

import bagu_chan.wild_delight.registry.ModBlocks;
import bagu_chan.wild_delight.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class CraftingGenerator extends RecipeProvider {
    public CraftingGenerator(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FERTILIZER.get(), 1)
                .requires(ModItems.FERTILIZER_BASE.get())
                .requires(Items.BONE_MEAL)
                .unlockedBy("has_item", has(ModItems.FERTILIZER_BASE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HAY_CARPET.get())
                .pattern("WW")
                .define('W', Items.WHEAT)
                .unlockedBy("has_item", has(Items.WHEAT))
                .save(consumer);

    }
}

package bagu_chan.wild_delight.compat;

import bagu_chan.wild_delight.WildDelight;
import bagu_chan.wild_delight.registry.ModBlocks;
import bagu_chan.wild_delight.registry.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final ResourceLocation PLUGIN_ID = new ResourceLocation(WildDelight.MODID, "jei_plugin");

    private static final Minecraft MC = Minecraft.getInstance();

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        addInfo(registration, ModItems.FERTILIZER.get());
        addInfo(registration, ModItems.BONE_MEAL.get());
        addInfo(registration, ModBlocks.HAY_CARPET.get().asItem());
        addInfo(registration, ModBlocks.DIRTY_HAY_CARPET.get().asItem(), ModBlocks.HAY_CARPET.get().asItem());
    }

    private static void addInfo(IRecipeRegistration registration, Item item) {
        registration.addIngredientInfo(
                new ItemStack(item),
                VanillaTypes.ITEM_STACK,
                Component.translatable(WildDelight.MODID + "." + ForgeRegistries.ITEMS.getKey(item).getPath() + ".jei_desc"));
    }

    private static void addInfo(IRecipeRegistration registration, Item item, Item originalDescItem) {
        registration.addIngredientInfo(
                new ItemStack(item),
                VanillaTypes.ITEM_STACK,
                Component.translatable(WildDelight.MODID + "." + ForgeRegistries.ITEMS.getKey(originalDescItem).getPath() + ".jei_desc"));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }
}
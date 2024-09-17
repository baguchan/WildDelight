package bagu_chan.wild_delight.registry;

import bagu_chan.wild_delight.WildDelight;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> MINECRAFT_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WildDelight.MODID);

    public static final RegistryObject<Item> BONE_MEAL = MINECRAFT_ITEM_REGISTRY.register("bone_meal", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> FERTILIZER_BASE = ITEMS.register("fertilizer_base", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> FERTILIZER = ITEMS.register("fertilizer", () -> new BoneMealItem((new Item.Properties())));

    public static final RegistryObject<Item> WILD_CHEF_SPAWNEGG = ITEMS.register("wild_chef_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.WILD_CHEF, 9804699, 0x81052d, (new Item.Properties())));
}
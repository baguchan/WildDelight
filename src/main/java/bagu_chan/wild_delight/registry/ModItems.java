package bagu_chan.wild_delight.registry;

import bagu_chan.wild_delight.WildDelight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WildDelight.MODID);

    public static final RegistryObject<Item> WILD_CHEF_SPAWNEGG = ITEM_REGISTRY.register("wild_chef_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.WILD_CHEF, 9804699, 0x81052d, (new Item.Properties())));
}
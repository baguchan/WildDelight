package bagu_chan.wild_delight.registry;

import bagu_chan.wild_delight.WildDelight;
import bagu_chan.wild_delight.block.HayCarpetBlock;
import bagu_chan.wild_delight.block.RichSoilFarmlandRevampedBlock;
import bagu_chan.wild_delight.block.RichSoilRevampedBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.RichSoilBlock;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WildDelight.MODID);

    public static final DeferredRegister<Block> FARMERS_BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "farmersdelight");
    public static final RegistryObject<RichSoilBlock> RICH_SOIL = FARMERS_BLOCK_REGISTRY.register("rich_soil", () -> {
        return new RichSoilRevampedBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).randomTicks());
    });
    public static final RegistryObject<RichSoilFarmlandBlock> RICH_SOIL_FARMLAND = FARMERS_BLOCK_REGISTRY.register("rich_soil_farmland", () -> {
        return new RichSoilFarmlandRevampedBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND));
    });

    public static final RegistryObject<Block> HAY_CARPET = register("hay_carpet", () -> new HayCarpetBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.4F).randomTicks().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> DIRTY_HAY_CARPET = register("dirty_hay_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.4F).sound(SoundType.MUD)));


    private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        Supplier<? extends Item> itemSupplier = item.apply(register);
        ModItems.ITEMS.register(name, itemSupplier);
        return register;
    }

    private static <T extends Block> RegistryObject<T> noItemRegister(String name, Supplier<? extends T> block) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        return register;
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
        return (RegistryObject<B>) baseRegister(name, block, (object) -> ModBlocks.registerBlockItem(object));
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
        return () -> {
            return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
        };
    }
}

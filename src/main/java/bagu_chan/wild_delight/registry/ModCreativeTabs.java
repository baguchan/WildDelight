package bagu_chan.wild_delight.registry;

import bagu_chan.wild_delight.WildDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WildDelight.MODID);


        public static final RegistryObject<CreativeModeTab> WILD_DELIGHT = CREATIVE_MODE_TABS.register(WildDelight.MODID, () -> CreativeModeTab.builder()
                .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                .title(Component.translatable("itemGroup." + WildDelight.MODID))
                .icon(() -> ModItems.WILD_CHEF_SPAWNEGG.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    output.acceptAll(Stream.of(
                            ModItems.WILD_CHEF_SPAWNEGG
                    ).map(sup -> {
                        return sup.get().getDefaultInstance();
                    }).toList());
                }).build());
}

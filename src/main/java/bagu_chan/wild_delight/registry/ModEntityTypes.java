package bagu_chan.wild_delight.registry;

import bagu_chan.wild_delight.WildDelight;
import bagu_chan.wild_delight.entity.ButterFly;
import bagu_chan.wild_delight.entity.WildChef;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = WildDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WildDelight.MODID);

    public static final RegistryObject<EntityType<WildChef>> WILD_CHEF = ENTITIES.register("wild_chef", () -> EntityType.Builder.of(WildChef::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F).clientTrackingRange(10).build(WildDelight.MODID + ":wild_chef"));
    public static final RegistryObject<EntityType<ButterFly>> BUTTERFLY = ENTITIES.register("butterfly", () -> EntityType.Builder.of(ButterFly::new, MobCategory.AMBIENT)
            .sized(0.3F, 0.3F).clientTrackingRange(6).build(WildDelight.MODID + ":butterfly"));

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(WILD_CHEF.get(), WildChef.createAttributes().build());
        event.put(BUTTERFLY.get(), ButterFly.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerEntityAttribute(SpawnPlacementRegisterEvent event) {
        event.register(WILD_CHEF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(BUTTERFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ButterFly::checkBatterflySpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
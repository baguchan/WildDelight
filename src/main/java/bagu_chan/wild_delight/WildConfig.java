package bagu_chan.wild_delight;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WildConfig {
    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {

        public final ForgeConfigSpec.BooleanValue richSoilTurnToFarmland;

        public Common(ForgeConfigSpec.Builder builder) {
            richSoilTurnToFarmland = builder
                    .translation(WildDelight.MODID + ".config.rich_soil_turn_to_farmland")
                    .comment("Make Rich Soil Turn To Farmland if fertilise is gone.")
                    .define("Rich Soil Turn To Farmland", false);
        }
    }
}
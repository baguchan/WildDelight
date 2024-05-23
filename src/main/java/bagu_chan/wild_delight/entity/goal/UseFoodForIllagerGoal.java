package bagu_chan.wild_delight.entity.goal;

import bagu_chan.wild_delight.entity.WildChef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModSounds;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static vectorwing.farmersdelight.common.item.SkilletItem.getCookingRecipe;

public class UseFoodForIllagerGoal<T extends Mob> extends Goal {
    private final Predicate<LivingEntity> fillter = (entity) -> {
        return entity.getHealth() < entity.getMaxHealth() * 0.75F;
    };
    private final T mob;
    private AbstractIllager target;
    private int cooldown = 100;
    private int eatTime;

    public UseFoodForIllagerGoal(T p_25972_) {
        this.mob = p_25972_;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        if(--this.cooldown <= 0) {
            if (this.mob.getOffhandItem().getFoodProperties(this.mob) != null) {
                List<AbstractIllager> list = this.mob.level().getNearbyEntities(AbstractIllager.class, TargetingConditions.forNonCombat(), this.mob, this.mob.getBoundingBox().expandTowards(16.0D, 8.0D, 16.0D));

                if (!list.isEmpty()) {
                    cooldown = 40 + this.mob.getRandom().nextInt(40);
                    return true;
                } else {
                    cooldown = 100 + this.mob.getRandom().nextInt(100);
                }
            }
        }

        return false;
    }

    public boolean canContinueToUse() {
        return this.target != null && this.mob.getOffhandItem().getFoodProperties(this.mob) != null && this.eatTime < 600;
    }

    public void start() {
        this.eatTime = 0;
        this.mob.setAggressive(false);
    }

    @Override
    public void tick() {
        super.tick();

        if(target != null&& target.isAlive()) {
            this.mob.getNavigation().moveTo(target, 1.2F);
            if (this.mob.getOffhandItem().getFoodProperties(this.target) != null) {
                target.heal(this.mob.getOffhandItem().getFoodProperties(this.target).getNutrition());
                this.eatTime = 1000;
            }
        }
    }

    public void stop() {
        this.target = null;
    }

}

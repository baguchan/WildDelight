package bagu_chan.wild_delight.entity.goal;

import bagu_chan.wild_delight.entity.WildChef;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class UseFoodForIllagerGoal<T extends WildChef> extends Goal {
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
            List<AbstractIllager> list = this.mob.level().getNearbyEntities(AbstractIllager.class, TargetingConditions.forNonCombat(), this.mob, this.mob.getBoundingBox().expandTowards(16.0D, 8.0D, 16.0D));

            if (!list.isEmpty() && !findFood().isEmpty()) {
                cooldown = 40 + this.mob.getRandom().nextInt(40);
                    target = list.get(this.mob.getRandom().nextInt(list.size()));
                    return true;
                } else {
                    cooldown = 50 + this.mob.getRandom().nextInt(100);
                }
        }

        return false;
    }

    private ItemStack findFood() {
        for (int i = 0; i < this.mob.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = this.mob.getInventory().getItem(i);
            if (!itemstack.isEmpty() && itemstack.getFoodProperties(this.mob) != null) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
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

        if(target != null&& target.isAlive() && this.mob.distanceToSqr(target) < 8) {
            this.mob.getNavigation().moveTo(target, 1.1F);
            ItemStack stack = findFood().split(1);
            if (stack.getFoodProperties(this.target) != null) {
                target.heal(stack.getFoodProperties(this.target).getNutrition());
                this.eatTime = 1000;
                this.mob.playSound(SoundEvents.GENERIC_EAT, 1F, 1F);
            }
        }
    }

    public void stop() {
        this.target = null;
    }

}

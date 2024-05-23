package bagu_chan.wild_delight.entity.goal;

import bagu_chan.wild_delight.entity.WildChef;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModSounds;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

import static vectorwing.farmersdelight.common.item.SkilletItem.getCookingRecipe;

public class UseSkilletGoal<T extends Mob> extends Goal {
    private final T mob;
    private int cooldown = 100;

    public UseSkilletGoal(T p_25972_) {
        this.mob = p_25972_;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        if(--this.cooldown <= 0) {
            if (this.mob.getMainHandItem().is(ModItems.SKILLET.get()) && this.mob.getOffhandItem().getFoodProperties(this.mob) != null) {
                InteractionHand otherHand = InteractionHand.OFF_HAND;
                ItemStack cookingStack = this.mob.getItemInHand(otherHand);
                Optional<CampfireCookingRecipe> recipe = getCookingRecipe(cookingStack, this.mob.level());
                if (recipe.isPresent()) {
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
        return this.mob.isUsingItem();
    }

    public void start() {
        InteractionHand otherHand = InteractionHand.OFF_HAND;
        ItemStack cookingStack = this.mob.getItemInHand(otherHand);
        ItemStack skilletStack = this.mob.getItemInHand(InteractionHand.MAIN_HAND);
        if (skilletStack.getOrCreateTag().contains("Cooking")) {
            this.mob.startUsingItem(InteractionHand.MAIN_HAND);
        }

        Optional<CampfireCookingRecipe> recipe = getCookingRecipe(cookingStack, this.mob.level());
        if (recipe.isPresent()) {
            ItemStack cookingStackCopy = cookingStack.copy();
            ItemStack cookingStackUnit = cookingStackCopy.split(1);
            skilletStack.getOrCreateTag().put("Cooking", cookingStackUnit.serializeNBT());
            skilletStack.getOrCreateTag().putInt("CookTimeHandheld", ((CampfireCookingRecipe)recipe.get()).getCookingTime());
            this.mob.startUsingItem(InteractionHand.MAIN_HAND);
            this.mob.setItemInHand(otherHand, cookingStackCopy);
        }
        this.mob.setAggressive(false);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 pos = this.mob.position();
        double x = pos.x() + 0.5;
        double y = pos.y();
        double z = pos.z() + 0.5;
        if (this.mob.level().random.nextInt(50) == 0) {
            this.mob.level().playLocalSound(x, y, z, (SoundEvent) ModSounds.BLOCK_SKILLET_SIZZLE.get(), SoundSource.BLOCKS, 0.4F, this.mob.getRandom().nextFloat() * 0.2F + 0.9F, false);
        }
    }

    public void stop() {
        this.mob.setItemSlot(EquipmentSlot.MAINHAND, this.finishUsingItem(this.mob.getItemBySlot(EquipmentSlot.MAINHAND), this.mob.level(),  this.mob));
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof WildChef player) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("Cooking")) {
                ItemStack cookingStack = ItemStack.of(tag.getCompound("Cooking"));
                Optional<CampfireCookingRecipe> cookingRecipe = getCookingRecipe(cookingStack, level);
                cookingRecipe.ifPresent((recipe) -> {
                    ItemStack resultStack = recipe.assemble(new SimpleContainer(new ItemStack[0]), level.registryAccess());
                    if (player.getInventory().canAddItem(resultStack)) {
                        player.getInventory().addItem(resultStack);
                    }else {
                        player.spawnAtLocation(resultStack);
                    }
                });
                tag.remove("Cooking");
                tag.remove("CookTimeHandheld");
            }
        }

        return stack;
    }

}

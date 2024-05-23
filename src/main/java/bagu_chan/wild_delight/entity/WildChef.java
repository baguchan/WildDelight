package bagu_chan.wild_delight.entity;

import bagu_chan.wild_delight.entity.goal.UseFoodForIllagerGoal;
import bagu_chan.wild_delight.entity.goal.UseSkilletGoal;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.Nullable;
import java.util.Map;

public class WildChef extends AbstractIllager implements InventoryCarrier {
    private final SimpleContainer inventory = new SimpleContainer(5);
    public WildChef(EntityType<? extends AbstractIllager> p_32105_, Level p_32106_) {
        super(p_32105_, p_32106_);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new UseSkilletGoal<>(this));
        this.goalSelector.addGoal(1, new UseFoodForIllagerGoal<>(this));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2F, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    private ItemStack findFood() {
        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty() && itemstack.getFoodProperties(this) != null) {
                return itemstack.split(1);
            }
        }
        return ItemStack.EMPTY;
    }

    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive()) {

            if (!this.isUsingItem() && this.getOffhandItem().isEmpty()) {
                ItemStack stack = ItemStack.EMPTY;

                if (this.random.nextFloat() < 0.005F) {
                    stack = this.findFood();
                }

                if (!stack.isEmpty()) {
                    this.setItemSlot(EquipmentSlot.OFFHAND, stack);

                }
                if (this.getHealth() < this.getMaxHealth() && this.getOffhandItem().getFoodProperties(this) != null && this.random.nextFloat() < 0.006F) {
                    this.startUsingItem(InteractionHand.OFF_HAND);
                }
            }
        }

        super.aiStep();
    }

    @Override
    public ItemStack eat(Level p_21067_, ItemStack p_21068_) {
        if(p_21068_.getFoodProperties(this) != null){
            this.heal(p_21068_.getFoodProperties(this).getNutrition());
        }
        return super.eat(p_21067_, p_21068_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.MAX_HEALTH, 24.0).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.ARMOR, 2).add(Attributes.FOLLOW_RANGE, 20.0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VINDICATOR_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VINDICATOR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_34103_) {
        return SoundEvents.VINDICATOR_HURT;
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34088_, DifficultyInstance p_34089_, MobSpawnType p_34090_, @Nullable SpawnGroupData p_34091_, @Nullable CompoundTag p_34092_) {
        SpawnGroupData spawngroupdata = super.finalizeSpawn(p_34088_, p_34089_, p_34090_, p_34091_, p_34092_);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        RandomSource randomsource = p_34088_.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, p_34089_);
        this.populateDefaultEquipmentEnchantments(randomsource, p_34089_);
        return spawngroupdata;
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_219149_, DifficultyInstance p_219150_) {
        if (this.getCurrentRaid() == null) {
            ItemStack stack = new ItemStack(ModItems.SKILLET.get());
            this.setItemSlot(EquipmentSlot.MAINHAND, stack);
            this.getInventory().addItem(new ItemStack(Items.PORKCHOP, 10));
        }
    }

    public void applyRaidBuffs(int p_34079_, boolean p_34080_) {
        ItemStack $$2 = new ItemStack(ModItems.SKILLET.get());
        Raid $$3 = this.getCurrentRaid();
        int $$4 = 1;
        if (p_34079_ > $$3.getNumGroups(Difficulty.NORMAL)) {
            $$4 = 2;
        }
        Map<Enchantment, Integer> $$6 = Maps.newHashMap();

        boolean $$5 = this.random.nextFloat() <= $$3.getEnchantOdds();
        if ($$5) {

            $$6.put(Enchantments.SHARPNESS, Integer.valueOf($$4));

        }
        EnchantmentHelper.setEnchantments($$6, $$2);
        this.setItemSlot(EquipmentSlot.MAINHAND, $$2);
    }

    public boolean isAlliedTo(Entity p_34110_) {
        if (super.isAlliedTo(p_34110_)) {
            return true;
        } else if (p_34110_ instanceof LivingEntity && ((LivingEntity)p_34110_).getMobType() == MobType.ILLAGER) {
            return this.getTeam() == null && p_34110_.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    @Override
    public SimpleContainer getInventory() {
        return this.inventory;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_33300_) {
        super.addAdditionalSaveData(p_33300_);
        this.writeInventoryToTag(p_33300_);
    }

    public AbstractIllager.IllagerArmPose getArmPose() {
        return this.isAggressive() ? IllagerArmPose.ATTACKING : IllagerArmPose.NEUTRAL;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_33291_) {
        super.readAdditionalSaveData(p_33291_);
        this.readInventoryFromTag(p_33291_);
        this.setCanPickUpLoot(true);
    }
    @Override
    protected void pickUpItem(ItemEntity p_33296_) {
        ItemStack itemstack = p_33296_.getItem();
        if (itemstack.getItem() instanceof BannerItem) {
            super.pickUpItem(p_33296_);
        } else if (this.wantsItem(itemstack)) {
            this.onItemPickup(p_33296_);
            ItemStack itemstack1 = this.inventory.addItem(itemstack);
            if (itemstack1.isEmpty()) {
                p_33296_.discard();
            } else {
                itemstack.setCount(itemstack1.getCount());
            }
        }

    }

    private boolean wantsItem(ItemStack p_149745_) {
        return this.hasActiveRaid() && p_149745_.is(Items.WHITE_BANNER);
    }

    @Override
    public SlotAccess getSlot(int p_149743_) {
        int i = p_149743_ - 300;
        return i >= 0 && i < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, i) : super.getSlot(p_149743_);
    }
}

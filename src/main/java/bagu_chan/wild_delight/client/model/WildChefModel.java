package bagu_chan.wild_delight.client.model;

import bagu_chan.wild_delight.client.WildChefAnimation;
import bagu_chan.wild_delight.entity.WildChef;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import vectorwing.farmersdelight.common.registry.ModItems;

public class WildChefModel<T extends WildChef> extends IllagerModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart arms;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    public WildChefModel(ModelPart modelPart) {
        super(modelPart);
        this.root = modelPart;
        this.head = modelPart.getChild("head");
        this.hat = this.head.getChild("hat");
        this.hat.visible = true;
        this.arms = modelPart.getChild("arms");
        this.leftLeg = modelPart.getChild("left_leg");
        this.rightLeg = modelPart.getChild("right_leg");
        this.leftArm = modelPart.getChild("left_arm");
        this.rightArm = modelPart.getChild("right_arm");
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if(entity.getUseItem().is(ModItems.SKILLET.get())){
            if (entity.getMainArm() == HumanoidArm.RIGHT) {
                this.rightArm.xRot = 0.0F;
                this.leftArm.xRot = 0.0F;
                this.applyStatic(WildChefAnimation.right_cook);
            }else {
                this.rightArm.xRot = 0.0F;
                this.leftArm.xRot = 0.0F;
                this.applyStatic(WildChefAnimation.left_cook);
            }

            this.arms.visible = false;
            this.leftArm.visible = true;
            this.rightArm.visible = true;
        }
    }
}

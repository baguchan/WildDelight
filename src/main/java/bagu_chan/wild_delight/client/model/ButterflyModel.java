package bagu_chan.wild_delight.client.model;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import bagu_chan.wild_delight.client.animation.ButterflyAnimation;
import bagu_chan.wild_delight.entity.ButterFly;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ButterflyModel<T extends ButterFly> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart wing_r;
    private final ModelPart wing_l;

    public ButterflyModel(ModelPart root) {
        this.root = root.getChild("root");
        this.wing_r = this.root.getChild("wing_r");
        this.wing_l = this.root.getChild("wing_l");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition wing_r = root.addOrReplaceChild("wing_r", CubeListBuilder.create().texOffs(-6, 0).addBox(-4.0F, 0.0F, -3.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition wing_l = root.addOrReplaceChild("wing_l", CubeListBuilder.create().texOffs(-6, 0).mirror().addBox(0.0F, 0.0F, -3.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(ButterFly entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.flyAnimationState, ButterflyAnimation.fly, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
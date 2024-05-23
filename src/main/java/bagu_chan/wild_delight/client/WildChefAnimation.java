package bagu_chan.wild_delight.client;// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 4.10.1
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class WildChefAnimation {
	public static final AnimationDefinition right_cook = AnimationDefinition.Builder.withLength(0.0F)
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-59.7864F, -6.4905F, -3.7661F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-43.6983F, 41.9982F, 35.0006F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition left_cook = AnimationDefinition.Builder.withLength(0.0F)
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-48.0699F, -41.5608F, -30.7897F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-57.1012F, 8.4215F, 5.4121F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();
}
package cc.unilock.headpets.mixin;

import cc.unilock.headpets.config.HeadpetsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Unique
	private final PlayerEntity petter = (PlayerEntity) (Object) this;

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void headPetMoment(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!petter.isSpectator() && petter.isSneaking() && petter.getStackInHand(hand).isEmpty()) {
            // TODO: separate conditional for when i feel like adding support for more entities
            if (entity instanceof PlayerEntity pettee) {
                if (!petter.getWorld().isClient()) {
					petter.heal(HeadpetsConfig.INSTANCE.petter_heal_amount.value());
					pettee.heal(HeadpetsConfig.INSTANCE.pettee_heal_amount.value());

                    var petteeEyePos = pettee.getEyePos();
					((ServerWorld) pettee.getWorld()).spawnParticles(ParticleTypes.HEART, petteeEyePos.getX(), petteeEyePos.getY() + 0.5, petteeEyePos.getZ(), 1, 0, 0, 0, 0.1);
                } else {
                    petter.swingHand(hand);
                }

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}

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
    private void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!petter.isSpectator() && petter.isSneaking() && petter.getMainHandStack().isEmpty()) {
            // TODO: separate conditional for future support for more entities
            if (entity instanceof PlayerEntity pettee) {
                if (!petter.getWorld().isClient()) {
					petter.heal(HeadpetsConfig.CONFIG.petter_heal_amount);
					pettee.heal(HeadpetsConfig.CONFIG.pettee_heal_amount);

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

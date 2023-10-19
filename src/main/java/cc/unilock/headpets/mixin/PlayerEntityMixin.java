package cc.unilock.headpets.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void headPetMoment(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var petter = (PlayerEntity) (Object) this;

        if (!petter.isSpectator() && petter.isSneaking() && petter.getStackInHand(hand).isEmpty()) {
            // TODO: separate conditional for when i feel like adding support for more entities
            if (entity instanceof PlayerEntity pettee) {
                if (petter.getWorld() instanceof ServerWorld) {
                    var petteeEyePos = pettee.getEyePos();
                    var petteeWorld = (ServerWorld) pettee.getWorld();

                    petteeWorld.spawnParticles(ParticleTypes.HEART, petteeEyePos.x, petteeEyePos.y + 0.5, petteeEyePos.z, 1, 0, 0, 0, 0.1);
                } else {
                    petter.swingHand(hand);
                }

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}

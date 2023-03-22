package rocks.katiekatiekatie.headpets.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
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
        var interactor = (PlayerEntity) (Object) this;

        if(interactor instanceof ServerPlayerEntity petter && !petter.getWorld().isClient()) {
            if (!petter.isSpectator() && petter.isSneaking() && petter.getActiveHand().equals(Hand.MAIN_HAND) && petter.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                // separate conditional for when i feel like adding support for more entities
                if (entity instanceof ServerPlayerEntity pettee) {
                    var petteeEyePos = pettee.getEyePos();

                    petter.getWorld().spawnParticles(ParticleTypes.HEART, petteeEyePos.x, petteeEyePos.y + 0.5, petteeEyePos.z, 1, 0, 0, 0, 0.1);

                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}

package com.nyfaria.manaunification.mixin;

import com.chiyukiruon.put_mana_in.Config;
import com.chiyukiruon.put_mana_in.DebugLogger;
import com.chiyukiruon.put_mana_in.PutManaIn;
import com.hollingsworth.arsnouveau.api.source.ISourceTile;
import com.nyfaria.manaunification.cap.ManaHolder;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PutManaIn.class)
public class PutManaInMixin {

    @Inject(method = "rightClickBlock", at = @At("HEAD"), cancellable = true, remap = false)
    public void rightClickBlock(PlayerInteractEvent.RightClickBlock event, CallbackInfo ci) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (Config.needEmptyHand && !player.getMainHandItem().isEmpty()) return;
        if (Config.noCrouching && player.isShiftKeyDown()) return;

        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(player);
        if (holder == null) return;

        ServerLevel serverLevel = (ServerLevel) event.getLevel();
        BlockEntity block = serverLevel.getBlockEntity(event.getPos());

        if (block instanceof ISourceTile sourceTile) {
            int currentSource = sourceTile.getSource();
            int maxSource = sourceTile.getMaxSource();

            DebugLogger.debug(player, "Transfer target: {}", block.getBlockPos());
            DebugLogger.debug(player, "Target max mana: {}", maxSource);

            if (currentSource < maxSource) {
                double availableMana = holder.getCurrentMana();
                double transferableMana = availableMana * Config.transferRatio;

                if (Config.maxPerTrans > 0) {
                    transferableMana = Math.min(transferableMana, Config.maxPerTrans);
                }

                int actualTransfer = Math.min(maxSource - currentSource, (int) transferableMana);
                double manaCost = actualTransfer / Config.transferRatio;

                sourceTile.addSource(actualTransfer);
                holder.consumeMana(manaCost);
                serverLevel.sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        event.getPos().getX() + 0.5,
                        event.getPos().getY() + 0.5,
                        event.getPos().getZ() + 0.5,
                        Config.chargeParticleCount,
                        Config.chargeParticleRadius,
                        Config.chargeParticleRadius,
                        Config.chargeParticleRadius,
                        0.01
                );

                DebugLogger.debug(player, "Current mana in target: {}", sourceTile.getSource());
                DebugLogger.debug(player, "Transferred mana: {}", actualTransfer);
                DebugLogger.debug(player, "Mana cost: {}", manaCost);

                if (!Config.cancelRightClickEvent) {
                    player.swing(event.getHand(), true);
                    ci.cancel();
                    return;
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
            ci.cancel();
        }
    }
}

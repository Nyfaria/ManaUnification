package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.client.gui.GuiManaHUD;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiManaHUD.class)
public class ArsNouveauManaHudMixin {

    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true, remap = false)
    private static void renderOverlay(ForgeGui gui, GuiGraphics graphics, float partialTicks, int width, int height, CallbackInfo ci) {
        ci.cancel();
    }
}

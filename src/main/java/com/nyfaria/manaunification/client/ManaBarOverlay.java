package com.nyfaria.manaunification.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nyfaria.manaunification.ManaUnification;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import com.nyfaria.manaunification.config.ManaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaBarOverlay implements IGuiOverlay {

    private static final ResourceLocation MANA_BAR_TEXTURE = new ResourceLocation(ManaUnification.MODID, "textures/gui/manabar.png");

    private static final int TEXTURE_WIDTH = 81;
    private static final int TEXTURE_HEIGHT = 18;
    private static final int BAR_HEIGHT = 9;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || mc.options.hideGui) {
            return;
        }

        ManaHolderAttacher.getHolder(player).ifPresent(holder -> {
            double currentMana = holder.getCurrentMana();
            double maxMana = holder.getMaxMana();

            if (maxMana <= 0) return;

            float manaPercentage = (float) (currentMana / maxMana);
            manaPercentage = Math.max(0, Math.min(1, manaPercentage));

            int baseX = screenWidth / 2 - 91;
            int baseY = screenHeight - 41 - BAR_HEIGHT;

            if (ManaConfig.MANA_BAR_MOVE_FOR_ARMOR.get() && player.getAttributeValue(Attributes.ARMOR) > 0) {
                baseY -= 10;
            }

            int x = baseX + ManaConfig.MANA_BAR_X_OFFSET.get();
            int y = baseY + ManaConfig.MANA_BAR_Y_OFFSET.get();

            int filledWidth = (int) (TEXTURE_WIDTH * manaPercentage);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            guiGraphics.blit(MANA_BAR_TEXTURE, x, y, 0, 0, TEXTURE_WIDTH, BAR_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);

            if (filledWidth > 0) {
                guiGraphics.blit(MANA_BAR_TEXTURE, x, y, 0, BAR_HEIGHT, filledWidth, BAR_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            }

            if (ManaConfig.SHOW_MANA_VALUES.get()) {
                String manaText = (int) currentMana + "/" + (int) maxMana;
                int textWidth = mc.font.width(manaText);
                int textX = x + (TEXTURE_WIDTH - textWidth) / 2;
                int textY = y + (BAR_HEIGHT - mc.font.lineHeight) / 2 + 1;
                guiGraphics.drawString(mc.font, manaText, textX, textY, 0xFFFFFF, true);
            }

            RenderSystem.disableBlend();
        });
    }
}

package com.nyfaria.manaunification.cap;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.nyfaria.manaunification.api.IManaHolder;
import com.nyfaria.manaunification.network.NetworkHandler;
import dev._100media.capabilitysyncer.core.EntityCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.simple.SimpleChannel;

public class ManaHolder extends EntityCapability implements IManaHolder {

    private double currentMana = -1;
    private boolean initialized = false;

    protected ManaHolder(Entity entity) {
        super(entity);
    }

    @Override
    public double getCurrentMana() {
        if (!initialized) {
            double maxMana = getMaxMana();
            if (maxMana > 0) {
                this.currentMana = maxMana;
                this.initialized = true;
            } else {
                return ManaAttributes.DEFAULT_MAX_MANA;
            }
        }
        double maxMana = getMaxMana();
        if (this.currentMana > maxMana) {
            this.currentMana = maxMana;
            updateTracking();
        }
        return this.currentMana;
    }

    @Override
    public void setCurrentMana(double mana) {
        double maxMana = getMaxMana();
        if (maxMana <= 0) {
            maxMana = ManaAttributes.DEFAULT_MAX_MANA;
        }
        this.currentMana = Math.max(0, Math.min(mana, maxMana));
        this.initialized = true;
        updateTracking();
    }

    @Override
    public double getMaxMana() {
        double value = ManaAttributes.DEFAULT_MAX_MANA;
        if (entity instanceof LivingEntity living) {
            try {
                var attributes = living.getAttributes();
                if (attributes != null) {
                    var attribute = attributes.getInstance(ManaAttributes.MAX_MANA.get());
                    if (attribute != null) {
                        value = attribute.getValue();
                    }
                }
            } catch (Exception e) {
            }
        }
        return value + getArsNouveauMaxBonus();
    }

    @Override
    public double getManaRegen() {
        double value = ManaAttributes.DEFAULT_MANA_REGEN;
        if (entity instanceof LivingEntity living) {
            try {
                var attributes = living.getAttributes();
                if (attributes != null) {
                    var attribute = attributes.getInstance(ManaAttributes.MANA_REGEN.get());
                    if (attribute != null) {
                        value = attribute.getValue();
                    }
                }
            } catch (Exception e) {
            }
        }
        return value + getArsNouveauRegenBonus();
    }

    private double getArsNouveauMaxBonus() {
        if (!(entity instanceof Player player) || !ModList.get().isLoaded("ars_nouveau")) return 0;
        try {
            IManaCap mana = CapabilityRegistry.getMana(player).orElse(null);
            if (mana == null) return 0;
            return mana.getGlyphBonus() * ServerConfig.GLYPH_MAX_BONUS.get()
                    + mana.getBookTier() * ServerConfig.TIER_MAX_BONUS.get();
        } catch (Throwable t) {
            return 0;
        }
    }

    private double getArsNouveauRegenBonus() {
        if (!(entity instanceof Player player) || !ModList.get().isLoaded("ars_nouveau")) return 0;
        try {
            IManaCap mana = CapabilityRegistry.getMana(player).orElse(null);
            if (mana == null) return 0;
            return mana.getGlyphBonus() * ServerConfig.GLYPH_REGEN_BONUS.get()
                    + mana.getBookTier() * ServerConfig.TIER_REGEN_BONUS.get();
        } catch (Throwable t) {
            return 0;
        }
    }

    public void tickManaRegen() {
        if (getCurrentMana() < getMaxMana()) {
            addMana(getManaRegen() / 20.0);
        }
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("currentMana", this.currentMana);
        tag.putBoolean("initialized", this.initialized);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        this.initialized = nbt.getBoolean("initialized");
        if (this.initialized) {
            this.currentMana = nbt.getDouble("currentMana");
            double maxMana = getMaxMana();
            if (this.currentMana > maxMana) {
                this.currentMana = maxMana;
            }
        }
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), ManaHolderAttacher.LOCATION, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return NetworkHandler.INSTANCE;
    }
}
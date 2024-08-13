package com.nyfaria.examplemod.cap;

import com.nyfaria.examplemod.network.NetworkHandler;
import dev._100media.capabilitysyncer.core.EntityCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.simple.SimpleChannel;

public class ExampleHolder extends EntityCapability {
    private int example;

    protected ExampleHolder(Entity entity) {
        super(entity);
    }

    public int getExample() {
        return example;
    }

    public void setExample(int example) {
        this.example = example;
        updateTracking();
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();

        tag.putInt("example", this.example);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        this.example = nbt.getInt("example");
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), ExampleHolderAttacher.EXAMPLE_RL, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return NetworkHandler.INSTANCE;
    }
}

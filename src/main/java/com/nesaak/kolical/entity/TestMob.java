package com.nesaak.kolical.entity;

import com.nesaak.kolical.player.GamePlayer;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.ai.EntityAI;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.pathfinding.NavigableEntity;
import net.minestom.server.entity.type.monster.EntityZombie;
import net.minestom.server.event.item.ArmorEquipEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.SpawnEntityPacket;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;
import net.minestom.server.utils.binary.BinaryWriter;
import net.minestom.server.utils.time.TimeUnit;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TestMob extends EntityZombie implements NavigableEntity, EntityAI {

    private boolean small;
    private boolean hasArms;
    private boolean noBasePlate;
    private boolean setMarker;

    private Vector headRotation;
    private Vector bodyRotation;
    private Vector leftArmRotation;
    private Vector rightArmRotation;
    private Vector leftLegRotation;
    private Vector rightLegRotation;

    public TestMob(Position spawnPosition) {
        super(spawnPosition);
        setInvisible(true);
        ItemStack itemStack = new ItemStack(Material.CREEPER_HEAD, (byte) 1);
        setSmall(false);
        //((PlayerHeadMeta) itemStack.getItemMeta()).setPlayerSkin(PlayerSkin.fromUsername("Nesaak"));
        setHelmet(itemStack);
        goalSelectors.add(new MeleeAttackGoal(this, 2, 2, TimeUnit.SECOND));
        goalSelectors.add(new RandomLookAroundGoal(this, 20));
        goalSelectors.add(new RandomStrollGoal(this, 5));
        targetSelectors.add(new ClosestEntityTarget(this, 20, GamePlayer.class));
    }

    @Override
    public boolean addViewer(@NotNull Player player) {
        final boolean result = super.addViewer(player);
        final PlayerConnection playerConnection = player.getPlayerConnection();
        SpawnEntityPacket spawnLivingEntityPacket = new SpawnEntityPacket();
        spawnLivingEntityPacket.entityId = getEntityId();
        spawnLivingEntityPacket.uuid = getUuid();
        spawnLivingEntityPacket.type = getEntityType().getId();
        spawnLivingEntityPacket.position = getPosition();
        spawnLivingEntityPacket.data = 0;
        playerConnection.sendPacket(spawnLivingEntityPacket);
        playerConnection.sendPacket(getVelocityPacket());
        playerConnection.sendPacket(getMetadataPacket());
        // Equipments synchronization
        syncEquipments(playerConnection);
        if (hasPassenger()) {
            playerConnection.sendPacket(getPassengersPacket());
        }
        return result;
    }

    @Override
    protected void fillMetadataIndex(@NotNull BinaryWriter packet, int index) {
        super.fillMetadataIndex(packet, index);
        if (index == 14) {
            packet.writeByte((byte) 14);
            packet.writeByte(METADATA_BYTE);
            byte dataValue = 0;
            if (isSmall())
                dataValue |= 0x01;
            if (hasArms)
                dataValue |= 0x04;
            if (hasNoBasePlate())
                dataValue |= 0x08;
            if (hasMarker())
                dataValue |= 0x10;
            packet.writeByte(dataValue);
        } else if (index == 15) {
            packet.writeByte((byte) 15);
            packet.writeByte(METADATA_ROTATION);
            packet.writeFloat(getRotationX(headRotation));
            packet.writeFloat(getRotationY(headRotation));
            packet.writeFloat(getRotationZ(headRotation));
        } else if (index == 16) {
            packet.writeByte((byte) 16);
            packet.writeByte(METADATA_ROTATION);
            packet.writeFloat(getRotationX(bodyRotation));
            packet.writeFloat(getRotationY(bodyRotation));
            packet.writeFloat(getRotationZ(bodyRotation));
        } else if (index == 17) {
            packet.writeByte((byte) 17);
            packet.writeByte(METADATA_ROTATION);
            packet.writeFloat(getRotationX(leftArmRotation));
            packet.writeFloat(getRotationY(leftArmRotation));
            packet.writeFloat(getRotationZ(leftArmRotation));
        } else if (index == 18) {
            packet.writeByte((byte) 18);
            packet.writeByte(METADATA_ROTATION);
            packet.writeFloat(getRotationX(rightArmRotation));
            packet.writeFloat(getRotationY(rightArmRotation));
            packet.writeFloat(getRotationZ(rightArmRotation));
        } else if (index == 19) {
            packet.writeByte((byte) 19);
            packet.writeByte(METADATA_ROTATION);
            packet.writeFloat(getRotationX(leftLegRotation));
            packet.writeFloat(getRotationY(leftLegRotation));
            packet.writeFloat(getRotationZ(leftLegRotation));
        } else if (index == 20) {
            packet.writeByte((byte) 20);
            packet.writeByte(METADATA_ROTATION);
            packet.writeFloat(getRotationX(rightLegRotation));
            packet.writeFloat(getRotationY(rightLegRotation));
            packet.writeFloat(getRotationZ(rightLegRotation));
        }
    }

    @NotNull
    @Override
    public Consumer<BinaryWriter> getMetadataConsumer() {
        return packet -> {
            super.getMetadataConsumer().accept(packet);
            fillMetadataIndex(packet, 14);
            fillMetadataIndex(packet, 15);
            fillMetadataIndex(packet, 16);
            fillMetadataIndex(packet, 17);
            fillMetadataIndex(packet, 18);
            fillMetadataIndex(packet, 19);
            fillMetadataIndex(packet, 20);
        };
    }

    public boolean isSmall() {
        return small;
    }

    public void setSmall(boolean small) {
        this.small = small;
        sendMetadataIndex(14);

        if (small) {
            setBoundingBox(0.25f, 0.9875f, 0.25f);
        } else {
            setBoundingBox(0.5f, 1.975f, 0.5f);
        }
    }

    public boolean hasArms() {
        return hasArms;
    }

    public void setHasArms(boolean hasArms) {
        this.hasArms = hasArms;
        sendMetadataIndex(14);
    }

    public boolean hasNoBasePlate() {
        return noBasePlate;
    }

    public void setNoBasePlate(boolean noBasePlate) {
        this.noBasePlate = noBasePlate;
        sendMetadataIndex(14);
    }

    public boolean hasMarker() {
        return setMarker;
    }

    public void setMarker(boolean setMarker) {
        this.setMarker = setMarker;
        sendMetadataIndex(14);
    }

    public Vector getHeadRotation() {
        return headRotation;
    }

    public void setHeadRotation(Vector headRotation) {
        this.headRotation = headRotation;
        sendMetadataIndex(15);
    }

    public Vector getBodyRotation() {
        return bodyRotation;
    }

    public void setBodyRotation(Vector bodyRotation) {
        this.bodyRotation = bodyRotation;
        sendMetadataIndex(16);
    }

    public Vector getLeftArmRotation() {
        return leftArmRotation;
    }

    public void setLeftArmRotation(Vector leftArmRotation) {
        this.leftArmRotation = leftArmRotation;
        sendMetadataIndex(17);
    }

    public Vector getRightArmRotation() {
        return rightArmRotation;
    }

    public void setRightArmRotation(Vector rightArmRotation) {
        this.rightArmRotation = rightArmRotation;
        sendMetadataIndex(18);
    }

    public Vector getLeftLegRotation() {
        return leftLegRotation;
    }

    public void setLeftLegRotation(Vector leftLegRotation) {
        this.leftLegRotation = leftLegRotation;
        sendMetadataIndex(19);
    }

    public Vector getRightLegRotation() {
        return rightLegRotation;
    }

    public void setRightLegRotation(Vector rightLegRotation) {
        this.rightLegRotation = rightLegRotation;
        sendMetadataIndex(20);
    }

    private float getRotationX(Vector vector) {
        return vector != null ? vector.getX() : 0;
    }

    private float getRotationY(Vector vector) {
        return vector != null ? vector.getY() : 0;
    }

    private float getRotationZ(Vector vector) {
        return vector != null ? vector.getZ() : 0;
    }

    // Equipments

    private ItemStack getEquipmentItem(@NotNull ItemStack itemStack, @NotNull ArmorEquipEvent.ArmorSlot armorSlot) {
        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(this, itemStack, armorSlot);
        callEvent(ArmorEquipEvent.class, armorEquipEvent);
        return armorEquipEvent.getArmorItem();
    }
}

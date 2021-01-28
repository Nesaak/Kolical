package com.nesaak.kolical.block.blocks;

import com.nesaak.kolical.block.GameBlock;
import com.nesaak.kolical.item.stat.ItemStat;
import com.nesaak.kolical.player.GamePlayer;
import net.minestom.server.data.Data;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class HardStone extends GameBlock {

    private static final UpdateOption UPDATE_OPTION = new UpdateOption(20, TimeUnit.TICK);

    public HardStone() {
        super(Block.BONE_BLOCK, "hard_stone");
    }

    @Override
    public void onPlace(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        for (Player player : instance.getPlayers()) {
            player.sendMessage(blockPosition.toString());
        }
    }

    @Override
    public void onDestroy(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
    }

    @Override
    public boolean onInteract(@NotNull Player player, Player.@NotNull Hand hand, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        return false;
    }

    @Override
    public void update(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        final short blockId = instance.getBlockStateId(blockPosition);
        instance.refreshBlockStateId(blockPosition, (short) (blockId+1));
    }

    @Override
    public boolean enableCustomBreakDelay() {
        return true;
    }

    @Override
    public int getBreakDelay(@NotNull Player player, @NotNull BlockPosition position, byte stage, Set<Player> breakers) {
        return (int) (5.0 / ((GamePlayer) player).getStat(ItemStat.MINING_SPEED));
    }

    @Nullable
    @Override
    public UpdateOption getUpdateOption() {
        return UPDATE_OPTION;
    }

    @Override
    public short getCustomBlockId() {
        return 1;
    }
}

package com.nesaak.kolical;

import com.nesaak.kolical.events.PlayerLoginCallback;
import com.nesaak.kolical.player.GamePlayer;
import com.nesaak.kolical.tasks.ResourceUsage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.ping.ResponseData;
import net.minestom.server.ping.ResponseDataConsumer;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.time.UpdateOption;

import java.util.UUID;

public class KolicalServer implements ResponseDataConsumer {

    private static final int PORT = 25565;
    static KolicalServer server;

    public static void main(String[] args) {
        new KolicalServer();
    }

    private KolicalServer() {
        server = this;
        prepare();
        start();
    }



    public void prepare() {
        MinecraftServer.init();
        MojangAuth.init();

        MinecraftServer.getBenchmarkManager().enable(new UpdateOption(10 * 1000, TimeUnit.MILLISECOND));

        MinecraftServer.getSchedulerManager().buildTask(new ResourceUsage()).repeat(10, TimeUnit.TICK).schedule();

        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerLoginEvent.class, new PlayerLoginCallback());

        MinecraftServer.getConnectionManager().setPlayerProvider(GamePlayer::new);
    }

    public void start() {
        MinecraftServer.init().start("0.0.0.0", PORT, this);
    }

    @Override
    public void accept(PlayerConnection playerConnection, ResponseData responseData) {
        responseData.setMaxPlayer(MinecraftServer.getConnectionManager().getOnlinePlayers().size() + 1);
        responseData.setOnline(MinecraftServer.getConnectionManager().getOnlinePlayers().size());
        responseData.addPlayer("A name", UUID.randomUUID());
        responseData.addPlayer("Could be some message", UUID.randomUUID());
        responseData.setDescription("Testing");
    }
}

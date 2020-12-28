package com.nesaak.kolical;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.nesaak.kolical.events.EventHandler;
import com.nesaak.kolical.item.KolicalStackingRule;
import com.nesaak.kolical.item.weapon.Stick;
import com.nesaak.kolical.player.GamePlayer;
import com.nesaak.kolical.tasks.ResourceUsage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.ping.ResponseData;
import net.minestom.server.ping.ResponseDataConsumer;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.time.UpdateOption;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class KolicalServer implements ResponseDataConsumer {

    private static final int PORT = 25565;
    static KolicalServer server;

    private MongoClient databaseClient;
    private MongoDatabase database;
    private Reflections reflections = new Reflections("com.nesaak.kolical");

    public static void main(String[] args) {
        new KolicalServer();
    }

    private KolicalServer() {
        server = this;
        prepare();
        start();
    }

    private void prepare() {
        setupDatabase();

        MojangAuth.init();
        MinecraftServer.init();

        registerEvents();
        registerCommands();

        ItemStack.setDefaultStackingRule(KolicalStackingRule.LARGE);

        MinecraftServer.getBenchmarkManager().enable(new UpdateOption(10 * 1000, TimeUnit.MILLISECOND));
        MinecraftServer.getSchedulerManager().buildTask(new ResourceUsage()).repeat(10, TimeUnit.TICK).schedule();
        MinecraftServer.getConnectionManager().setPlayerProvider(GamePlayer::new);

        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerBlockInteractEvent.class, click -> {
            click.getPlayer().sendMessage(click.getPlayer().getItemInMainHand().getClass().getName());
        });

        Stick stick = new Stick();
        stick.setHits(50);
        System.out.println("Original Class: " + stick.getHits());
        System.out.println("Cloned Class: " + ((Stick) stick.clone()).getHits());

    }

    private void registerEvents() {
        reflections.getSubTypesOf(EventHandler.class).forEach(event -> {
            try {
                EventHandler handler = event.newInstance();
                MinecraftServer.getGlobalEventHandler().addEventCallback(handler.getEventClass(), handler);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerCommands() {
        reflections.getSubTypesOf(Command.class).forEach(commandclz -> {
            try {
                Command command = commandclz.newInstance();
                MinecraftServer.getCommandManager().register(command);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void start() {
        MinecraftServer.init().start("0.0.0.0", PORT, this);
    }

    private void setupDatabase() {
        databaseClient = MongoClients.create(readResource("Mongo.txt").get(0));
        database = databaseClient.getDatabase("development");
    }

    public List<String> readResource(String name) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(name), Charset.defaultCharset()));
        return reader.lines().collect(Collectors.toList());
    }

    public MongoDatabase getDatabase() {
        return database;
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

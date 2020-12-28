package com.nesaak.kolical.world;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.DimensionType;
import net.minestom.server.world.biomes.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class GameInstance {

    private static Instance mainInstance;

    static {
        mainInstance = MinecraftServer.getInstanceManager().createInstanceContainer(DimensionType.OVERWORLD);
        mainInstance.enableAutoChunkLoad(true);
        mainInstance.setChunkGenerator(new ChunkGenerator() {
            @Override
            public void generateChunkData(ChunkBatch chunkBatch, int i, int i1) {
                for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++)
                    for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                        for (byte y = 0; y < 40; y++) {
                            chunkBatch.setBlock(x, y, z, Block.STONE);
                        }
                    }
            }

            @Override
            public void fillBiomes(Biome[] biomes, int i, int i1) {
                Arrays.fill(biomes, MinecraftServer.getBiomeManager().getById(0));
            }

            @Override
            public @Nullable List<ChunkPopulator> getPopulators() {
                return null;
            }
        });
    }

    public static Instance getMainInstance() {
        return mainInstance;
    }
}

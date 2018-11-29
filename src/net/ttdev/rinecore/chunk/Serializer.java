package net.ttdev.rinecore.chunk;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public final class Serializer {

    private static void serializeLandChunk(String filePath, AbstractChunk chunk, YamlConfiguration configuration) {

        final String sectionId = chunk.getOwner().toString();

        final ConfigurationSection section = configuration.isConfigurationSection(sectionId) ?
                configuration.getConfigurationSection(sectionId) :
                configuration.createSection(sectionId);

        final ConfigurationSection chunkSection = section.isConfigurationSection(chunk.getName()) ?
                section.getConfigurationSection(chunk.getName()) :
                section.createSection(chunk.getName());

        chunkSection.set("chunk-x", chunk.getChunkX());
        chunkSection.set("chunk-z", chunk.getChunkZ());

        if (chunk instanceof RentedChunk) {
            final RentedChunk rentedChunk = (RentedChunk) chunk;
            final int timeLeft = rentedChunk.getTimeLeft();
            chunkSection.set("time-left", timeLeft);
        }

    }

    public static void serializeLandChunk(String filePath, AbstractChunk chunk) {

        File file = new File(filePath);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        serializeLandChunk(filePath, chunk, configuration);

        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a <code>landChunks</code> to the file denoted by
     * <code>filePath</code>.
     *
     * @param filePath
     * @param landChunks
     */
    public static void serializeLandChunks(String filePath, Collection<? extends AbstractChunk> landChunks) {

        File file = new File(filePath);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        for (AbstractChunk chunk : landChunks) {
            serializeLandChunk(filePath, chunk, configuration);
        }

        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads and returns a <code>List</code> of all the <code>net.ttdev.rinecore.chunk.AbstractChunk</code>'s owned by <code>owner</code>.
     *
     * @param owner
     * @param filePath
     * @return
     */
    public static List<AbstractChunk> deserializeLandChunks(UUID owner, String filePath) {

        File file = new File(filePath);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final ConfigurationSection section = configuration.getConfigurationSection(owner.toString());
        final Set<String> keySet = section.getKeys(false);
        final Iterator<String> keyIterator = keySet.iterator();

        final List<AbstractChunk> chunks = new ArrayList<>();
        while (keyIterator.hasNext()) {
            final String chunkName = keyIterator.next();
            final ConfigurationSection chunkSection = section.getConfigurationSection(chunkName);
            final int chunkX = chunkSection.getInt("chunk-x");
            final int chunkZ = chunkSection.getInt("chunk-z");

            final int rentTime = chunkSection.getInt("rent-time", -1);
            AbstractChunk chunk = rentTime == -1 ?
                    new OwnedChunk(owner, chunkName, chunkX, chunkZ) :
                    new RentedChunk(owner, chunkName, chunkX, chunkZ, rentTime);

            chunks.add(chunk);
        }

        return chunks;
    }

}

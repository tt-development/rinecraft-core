package net.ttdev.rinecore.file;

import net.ttdev.rinecore.chunk.AbstractLand;
import net.ttdev.rinecore.chunk.OwnedLand;
import net.ttdev.rinecore.chunk.RentedLand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

/**
 * Used for saving and loading various custom objects.
 */
public final class Serializer {

    private static ConfigurationSection searchConfigurationSection(String sectionId, ConfigurationSection section) {
        return section.isConfigurationSection(sectionId) ?
                section.getConfigurationSection(sectionId) :
                section.createSection(sectionId);
    }

    public static Integer loadBalance(UUID uuid, String filePath) {

        final File file = new File(filePath);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final String sectionId = uuid.toString();
        final ConfigurationSection section = searchConfigurationSection(sectionId, configuration);

        return section.getInt("balance");
    }

    public static void saveBalance(UUID uuid, int amount, String filePath) {

        final File file = new File(filePath);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final String sectionId = uuid.toString();
        final ConfigurationSection section = searchConfigurationSection(sectionId, configuration);

        section.set("balance", amount);

        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveChunk(AbstractLand chunk, YamlConfiguration configuration) {

        final String sectionId = chunk.getOwner().toString();
        final ConfigurationSection section = searchConfigurationSection(sectionId, configuration);
        ConfigurationSection chunkSection = searchConfigurationSection(chunk.getName(), section);

        if (chunk instanceof RentedLand) {

            final RentedLand rentedChunk = (RentedLand) chunk;

            if (rentedChunk.hasExpired()) {
                section.set(chunk.getName(), null);
                System.out.println("Deleting rent: " + chunk.getName() + ".");
                return;
            }

            final int duration = rentedChunk.getDuration();
            chunkSection.set("duration", duration);
        }

        chunkSection.set("chunk-x", chunk.getChunkX());
        chunkSection.set("chunk-z", chunk.getChunkZ());

    }

    public static void saveChunk(String filePath, AbstractLand chunk) {

        File file = new File(filePath);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        saveChunk(chunk, configuration);

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
    public static void saveChunks(String filePath, Collection<? extends AbstractLand> landChunks) {

        File file = new File(filePath);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        for (AbstractLand chunk : landChunks) {
            saveChunk(chunk, configuration);
        }

        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads and returns a <code>List</code> of all the <code>AbstractChunk</code>'s owned by <code>owner</code>.
     *
     * @param owner
     * @param filePath
     * @return
     */
    public static List<AbstractLand> loadChunks(UUID owner, String filePath) {

        File file = new File(filePath);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final ConfigurationSection section = searchConfigurationSection(owner.toString(), configuration);

        final Set<String> keySet = section.getKeys(false);
        final Iterator<String> keyIterator = keySet.iterator();

        final List<AbstractLand> chunks = new ArrayList<>();
        while (keyIterator.hasNext()) {
            final String chunkName = keyIterator.next();
            final ConfigurationSection chunkSection = section.getConfigurationSection(chunkName);
            final int chunkX = chunkSection.getInt("chunk-x");
            final int chunkZ = chunkSection.getInt("chunk-z");

            final int duration = chunkSection.getInt("duration", -1);
            AbstractLand chunk = duration == -1 ?
                    new OwnedLand(owner, chunkName, chunkX, chunkZ) :
                    new RentedLand(owner, chunkName, chunkX, chunkZ, duration);

            chunks.add(chunk);
        }

        return chunks;
    }

}

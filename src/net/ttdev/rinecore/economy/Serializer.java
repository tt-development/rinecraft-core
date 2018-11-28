package net.ttdev.rinecore.economy;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class Serializer {

    private static void serializeLandChunk(String filePath, LandChunk landChunk, boolean saveFile) {

        File file = new File(filePath);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final String sectionId = landChunk.getOwner().toString();

        final ConfigurationSection section = configuration.isConfigurationSection(sectionId) ?
                configuration.getConfigurationSection(sectionId) :
                configuration.createSection(sectionId);

        final ConfigurationSection chunkSection = section.isConfigurationSection(landChunk.getName()) ?
                section.getConfigurationSection(landChunk.getName()) :
                section.createSection(landChunk.getName());

        chunkSection.set("chunk-x", landChunk.getChunkX());
        chunkSection.set("chunk-z", landChunk.getChunkZ());

        if (landChunk instanceof RentedLandChunk) {
            final RentedLandChunk rentedLandChunk = (RentedLandChunk) landChunk;
            final int timeLeft = rentedLandChunk.getTimeLeft();
            chunkSection.set("time-left", timeLeft);
        }

        if (!saveFile) return;

        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void serializeLandChunk(String filePath, LandChunk landChunk) {
        serializeLandChunk(filePath, landChunk, true);
    }

    /**
     * Save a <code>landChunks</code> to the file denoted by
     * <code>filePath</code>.
     *
     * @param filePath
     * @param landChunks
     */
    public static void serializeLandChunks(String filePath, List<? extends LandChunk> landChunks) {

        File file = new File(filePath);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        for (LandChunk landChunk : landChunks) {
            serializeLandChunk(filePath, landChunk, false);
        }

        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads and returns a <code>List</code> of all the <code>LandChunk</code>'s owned by <code>owner</code>.
     *
     * @param owner
     * @param filePath
     * @return
     */
    public static List<LandChunk> deserializeLandChunks(UUID owner, String filePath) {

        File file = new File(filePath);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final ConfigurationSection section = configuration.getConfigurationSection(owner.toString());
        final Set<String> keySet = section.getKeys(false);
        final Iterator<String> keyIterator = keySet.iterator();

        final List<LandChunk> landChunks = new ArrayList<>();
        while (keyIterator.hasNext()) {
            final String chunkName = keyIterator.next();
            final ConfigurationSection chunkSection = section.getConfigurationSection(chunkName);
            final int chunkX = chunkSection.getInt("chunk-x");
            final int chunkZ = chunkSection.getInt("chunk-z");

            final int rentTime = chunkSection.getInt("rent-time", -1);
            LandChunk landChunk = rentTime == -1 ?
                    new OwnedLandChunk(owner, chunkName, chunkX, chunkZ) :
                    new RentedLandChunk(owner, chunkName, chunkX, chunkZ, rentTime);

            landChunks.add(landChunk);
        }

        return landChunks;
    }

}

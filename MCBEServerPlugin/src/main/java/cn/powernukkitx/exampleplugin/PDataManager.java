package cn.powernukkitx.exampleplugin;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class PDataManager {

    private static final String FILE_NAME = "playerData.dat";
    private static final byte VERSION = 1;

    private Map<UUID, CustomPlayer> pList;
    File file;
    Plugin plugin;


    public PDataManager(Plugin plugin)
    {
        this.plugin = plugin;
        loadAllPlayerData();
    }

    public void setpList(Map<UUID, CustomPlayer> pList) {
        this.pList = pList;
    }

    public Map<UUID, CustomPlayer> getpList() {
        return pList;
    }

    public void savePlayerData() {
        try (OutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {

            oos.writeByte(VERSION);
            oos.writeObject(pList);
        } catch (IOException e) {
            plugin.getLogger().critical("<CRITICAL> Error saving player data.");
        }
    }

    public void loadAllPlayerData() {
        if (file.exists()) {
            try (InputStream fileIn = new FileInputStream(FILE_NAME);
                 ObjectInputStream ois = new ObjectInputStream(fileIn)) {

                byte version = ois.readByte();
                if (version == VERSION) {
                    pList = (Map<UUID, CustomPlayer>) ois.readObject();
                } else {
                    throw new IOException("Unsupported version: " + version);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlayer(Player player) {
        pList.put(player.getUniqueId(), new CustomPlayer(player));
    }

    public void remPlayer(UUID uuid) {
        pList.remove(uuid);
    }

}

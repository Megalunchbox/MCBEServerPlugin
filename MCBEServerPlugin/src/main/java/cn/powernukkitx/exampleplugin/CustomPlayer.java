package cn.powernukkitx.exampleplugin;

import cn.nukkit.Player;
import cn.nukkit.network.connection.BedrockSession;
import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.PacketHandler;
import cn.nukkit.network.protocol.types.PlayerInfo;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class CustomPlayer extends Player implements Serializable
{

    transient Player player;
    String[] permissions;
    byte gameMode;


    public CustomPlayer(Player player) {
        super(player.getSession(), player.getPlayerInfo());
        this.player = player;

    }

    public void debug() {

    }

}

package com.mygdx.game;

import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.network.Network;

import java.io.IOException;

/**
 * Created by Alan on 7/12/2017.
 */
public class GameClient extends Client {
    private static GameClient ourInstance = new GameClient();

    public static GameClient instance() {
        return ourInstance;
    }

    private GameClient() {
        super();
    }

    public void connect(String ip){
        try {
            GameClient.instance().connect(5000, ip, Network.port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

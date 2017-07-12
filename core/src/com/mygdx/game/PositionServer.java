package com.mygdx.game;


import java.io.IOException;
import java.util.HashSet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.network.Network;
import com.esotericsoftware.minlog.Log;

/**
 * Created by Alan on 7/2/2017.
 */
public class PositionServer {
    Server server;
    HashSet<Character> loggedIn = new HashSet();

    public PositionServer () throws IOException {
        server = new Server() {
            protected Connection newConnection () {
                return new UserConnection();
            }
        };

        Network.register(server);

        server.bind(Network.port);
        server.start();
    }

    // This holds per connection state.
    public static class UserConnection extends Connection {
        public User user;
    }

    public static void main (String[] args) throws IOException {
        Log.set(Log.LEVEL_DEBUG);
        new PositionServer();
    }
}

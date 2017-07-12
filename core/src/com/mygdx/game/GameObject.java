package com.mygdx.game;

import com.esotericsoftware.kryonet.*;
import com.mygdx.game.UIScreens.ClientUI;

/**
 * Created by Alan on 7/10/2017.
 */
public class GameObject {
    public String name;
    public int id, x, y;

    public static void create() {
        GameObject.RegisterGameObject rgo = new GameObject.RegisterGameObject();
        GameClient.instance().sendTCP(rgo);
    }

    public static void registerListeners(EndPoint endPoint) {
        if (endPoint instanceof Server) {
            final Server server = (Server) endPoint;
            endPoint.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    PositionServer.UserConnection con = (PositionServer.UserConnection)connection;

                    if (object instanceof RegisterGameObject) {
                        RegisterGameObject rgo = (RegisterGameObject) object;
                        GameObject go = new GameObject();
                        go.name = con.user.name;
                        go.id = GameObjectsById.instance().values().size();
                        go.x = 0;
                        go.y = 0;
                        GameObjects.instance().put(go);

                        AddGameObject ago = new AddGameObject();
                        ago.id = go.id;
                        ago.name = go.name;
                        ago.x = go.x;
                        ago.y = go.y;
                        server.sendToAllTCP(ago);
                    }

                    if (object instanceof UpdateGameObject) {
                        UpdateGameObject ago = (UpdateGameObject) object;
                        GameObject go = GameObjects.instance().get(con.user.name);
                        go.x = ago.x;
                        go.y = ago.y;

                        ago.id = go.id;

                        server.sendToAllTCP(ago);
                    }
                }

                public void disconnected (Connection c) {
                    PositionServer.UserConnection connection = (PositionServer.UserConnection)c;
                    if (connection.user != null) {
                    }
                }
            });
        }

        if (endPoint instanceof Client) {
            endPoint.addListener(new Listener() {
                public void received (Connection connection, Object object) {

                    if (object instanceof AddGameObject) {
                        AddGameObject ago = (AddGameObject) object;
                        GameObject go = new GameObject();
                        go.name = ago.name;
                        go.id = ago.id;
                        go.x = ago.x;
                        go.y = ago.y;

                        GameObjects.instance().put(go);
                    }

                    if (object instanceof UpdateGameObject) {
                        UpdateGameObject ago = (UpdateGameObject) object;
                        GameObject go = GameObjectsById.instance().get(ago.id);
                        if (go != null){
                            go.x = ago.x;
                            go.y = ago.y;
                        }
                    }
                }
            });
        }

    }
    public void update(ClientUI clientUi, float delta){
        if (name.equals(clientUi.name)) {
            UpdateGameObject request = new UpdateGameObject();
            request.id = id;
            request.x = x;
            request.y = y;
            GameClient.instance().sendTCP(request);
        }
    }

    public static class AddGameObject {
        int id, x, y;
        String name;
    }

    public static class UpdateGameObject {
        int id, x, y;
    }

    public static class RegisterGameObject {
    }
}

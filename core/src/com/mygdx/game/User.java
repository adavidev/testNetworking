package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.*;
import com.mygdx.game.UIScreens.Users;
import com.mygdx.game.network.SomeRequest;

/**
 * Created by Alan on 7/9/2017.
 */
public class User {
    public String name;

    public static void login(String name) {
        User.Login request = new User.Login();
        request.name = name;
        GameClient.instance().sendTCP(request);
    }

    public static void registerListeners(EndPoint endPoint) {
        if (endPoint instanceof Server) {
            final Server server = (Server) endPoint;
            endPoint.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    PositionServer.UserConnection con = (PositionServer.UserConnection)connection;

                    if (object instanceof User.Login) {
                        User.Login request = (User.Login) object;
                        System.out.println(request.name);

                        con.user = new User();
                        con.user.name = request.name;

                        SomeRequest response = new SomeRequest();
                        response.text = request.name + " just logged in";
                        server.sendToAllTCP(response);

                        // notify dude about everyone else in the server
                        for(User usr : Users.instance().values()){
                            User.Login log = new User.Login();
                            log.name = usr.name;
                            con.sendTCP(log);
                        }

                        for (GameObject go : GameObjectsById.instance().values()) {
                            GameObject.AddGameObject ago = new GameObject.AddGameObject();
                            ago.id = go.id;
                            ago.name = go.name;
                            ago.x = go.x;
                            ago.y = go.y;

                            con.sendTCP(ago);
                        }

                        //include dude in the User list
                        Users.instance().put(con.user);

                        // notify everyone that there's a new dude
                        server.sendToAllTCP(request);
                    }

                    if (object instanceof SomeRequest) {
                        SomeRequest request = (SomeRequest) object;
                        System.out.println(request.text);

                        SomeRequest response = new SomeRequest();
                        response.text = con.user.name + " said: " + request.text ;
                        server.sendToAllTCP(response);
                    }
                }

                public void disconnected (Connection c) {
                    PositionServer.UserConnection connection = (PositionServer.UserConnection)c;
                    if (connection.user != null) {
                        Users.instance().remove(connection.user);
                        Logout logout = new Logout();
                        logout.name = connection.user.name;
                        server.sendToAllTCP(logout);
                    }
                }
            });
        }

        if (endPoint instanceof Client) {
            endPoint.addListener(new Listener() {
                public void received (Connection connection, Object object) {

                    if (object instanceof User.Login) {
                        User.Login request = (User.Login) object;
                        System.out.println(request.name);

                        User user = new User();
                        user.name = request.name;
                        Users.instance().put(user);
                    }

                    if (object instanceof User.Logout) {
                        User.Logout request = (User.Logout) object;
                        System.out.println("Logout: " + request.name);
                        Users.instance().remove(request.name);
                    }

                    if (object instanceof SomeRequest) {
                        SomeRequest response = (SomeRequest) object;
                        System.out.println(response.text);
                    }
                }
            });
        }


    }

    public static class Login {
        public String name;
    }

    public static class Logout {
        public String name;
    }
}

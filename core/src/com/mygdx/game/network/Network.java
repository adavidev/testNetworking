
package com.mygdx.game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;
import com.mygdx.game.GameObject;
import com.mygdx.game.User;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 54555;

	public static void register(EndPoint connection){
		Kryo kryo = connection.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(User.Login.class);
		kryo.register(User.Logout.class);
		kryo.register(User.class);
		kryo.register(GameObject.class);
		kryo.register(GameObject.AddGameObject.class);
		kryo.register(GameObject.RegisterGameObject.class);
		kryo.register(GameObject.UpdateGameObject.class);

		registerListeners(connection);
	}

	private static void registerListeners(EndPoint endPoint) {
		User.registerListeners(endPoint);
		GameObject.registerListeners(endPoint);
	}
}

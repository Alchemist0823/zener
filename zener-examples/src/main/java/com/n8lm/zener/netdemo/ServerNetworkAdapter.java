package com.n8lm.zener.netdemo;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.ImmutableBag;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.n8lm.zener.netdemo.messages.AddCharacterMessage;
import com.n8lm.zener.netdemo.messages.LoginMessage;
import com.n8lm.zener.netdemo.messages.UpdateCharacterMessage;
import com.n8lm.zener.network.NetworkMessage;
import com.n8lm.zener.network.NetworkMessageAdapter;
import com.n8lm.zener.netdemo.messages.MoveMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class ServerNetworkAdapter implements NetworkMessageAdapter {

    private final World world;
    private final CharacterSystem cs;
    private Map<Integer, CharacterComponent> connectionMap;
    private Server server;

    public ServerNetworkAdapter(World world) {
        connectionMap = new HashMap<>();
        this.world = world;
        this.cs = world.getSystem(CharacterSystem.class);
    }

    @Override
    public void init(Server server) {
        this.server = server;
    }

    @Override
    public void init(Client client) {

    }

    @Override
    public void process(Connection connection, NetworkMessage message) {
        int cid = connection.getID();
        CharacterComponent cc;
        cc = connectionMap.get(cid);

        if (cc != null) {
            if (message instanceof MoveMessage) {
                switch (((MoveMessage) message).ch) {
                    case 'w':
                        cc.setY(cc.getY() + 1f);
                        break;
                    case 's':
                        cc.setY(cc.getY() - 1f);
                        break;
                    case 'a':
                        cc.setX(cc.getX() + 1f);
                        break;
                    case 'd':
                        cc.setX(cc.getX() - 1f);
                        break;
                }
                System.out.println(cc.getName() + " " + cc.getX() + " " + cc.getY());
                server.sendToAllUDP(new UpdateCharacterMessage(cc));
            }
        } else {
            if (message instanceof LoginMessage) {
                cc = new CharacterComponent(((LoginMessage) message).name, cs.getCharacterCount() + 1);
                connectionMap.put(connection.getID(), cc);

                System.out.println(cc.getName() + " Logged in id: " + cc.getId());


                ImmutableBag<Entity> entities = cs.getActives();
                for (int i = 0; i < entities.size(); i ++) {
                    CharacterComponent othercc = entities.get(i).getComponent(CharacterComponent.class);
                    server.sendToTCP(connection.getID(), new AddCharacterMessage(othercc.getName(), othercc.getId(), othercc.getX(), othercc.getY()));

                    System.out.println("send " + othercc.getId());
                }

                Entity character = world.createEntity();
                character.addComponent(cc);
                world.addEntity(character);

                server.sendToAllTCP(new AddCharacterMessage(cc.getName(), cc.getId(), cc.getX(), cc.getY()));
            }
        }
    }

    @Override
    public void connected(Connection connection) {

    }

    @Override
    public void disconnected(Connection connection) {
        if(connectionMap.containsKey(connection.getID())) {
            CharacterComponent cc = connectionMap.get(connection.getID());
            world.deleteEntity(cs.getEntityByCharacterID(cc.getId()));
            connectionMap.remove(connection.getID());
        }
    }
}

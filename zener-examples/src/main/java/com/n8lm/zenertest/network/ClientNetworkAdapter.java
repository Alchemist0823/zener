package com.n8lm.zenertest.network;

import com.artemis.Entity;
import com.artemis.World;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.network.NetworkMessage;
import com.n8lm.zener.network.NetworkMessageAdapter;
import com.n8lm.zener.utils.EntityFactory;
import com.n8lm.zenertest.network.messages.AddCharacterMessage;
import com.n8lm.zenertest.network.messages.UpdateCharacterMessage;
import org.lwjgl.Sys;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class ClientNetworkAdapter implements NetworkMessageAdapter {
    private final World world;
    private final CharacterSystem cs;
    private final NetworkTest game;
    private Client client;

    public ClientNetworkAdapter(World world, NetworkTest game) {
        this.world = world;
        this.game = game;
        cs = world.getSystem(CharacterSystem.class);
    }

    @Override
    public void init(Server server) {

    }

    @Override
    public void init(Client client) {
        this.client = client;
    }

    @Override
    public void process(Connection connection, NetworkMessage message) {
        if (message instanceof AddCharacterMessage) {
            AddCharacterMessage adc = (AddCharacterMessage) message;
            if (cs.getEntityByCharacterID(adc.id) == null) {
                Entity character = EntityFactory.createModelObject(Vector3f.ZERO, Quaternion.IDENTITY, null, null, "suzanne", false, false);
                character.addComponent(new CharacterComponent(adc.name, adc.id, adc.x, adc.y));
                world.addEntity(character);
                System.out.println("Add " + adc.id);
            }
        } else if (message instanceof UpdateCharacterMessage) {
            UpdateCharacterMessage updateCharacterMessage = (UpdateCharacterMessage) message;
            System.out.println(updateCharacterMessage.id + " " + updateCharacterMessage.x + " " + updateCharacterMessage.y);
            cs.updateCharacter(updateCharacterMessage.id, updateCharacterMessage.x, updateCharacterMessage.y);
        }
    }

    @Override
    public void connected(Connection connection) {

    }

    @Override
    public void disconnected(Connection connection) {
        game.close();
    }
}

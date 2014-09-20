package com.n8lm.zener.network;

import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014/9/17.
 *
 * @author Alchemist
 */
public abstract class NetworkConfiguration {

    protected List<MessageType> messageTypes;

    public NetworkConfiguration (){
        messageTypes = new ArrayList<MessageType>();
        addMessageTypes();
    }

    protected abstract void addMessageTypes();

    protected void addMessage(Class<? extends NetworkMessage> messageClass, Serializer<?> serializer) {
        messageTypes.add(new MessageType(messageClass, serializer));
    }

    protected void addMessage(Class<? extends NetworkMessage> messageClass) {
        messageTypes.add(new MessageType(messageClass, null));
    }

    public List<MessageType> getMessageTypes() {
        return messageTypes;
    }

    public abstract int getServerPort();

    public void register(EndPoint endPoint) {
        for (MessageType messageType : messageTypes) {
            if (messageType.getSerializer() != null)
                endPoint.getKryo().register(messageType.getMessageClass(), messageType.getSerializer());
            else
                endPoint.getKryo().register(messageType.getMessageClass());
        }
    }

    public class MessageType {
        private Class<? extends NetworkMessage> messageClass;
        private Serializer serializer;

        public MessageType(Class<? extends NetworkMessage> messageClass, Serializer<?> serializer) {
            this.setMessageClass(messageClass);
            this.setSerializer(serializer);
        }

        public Class<? extends NetworkMessage> getMessageClass() {
            return messageClass;
        }

        public void setMessageClass(Class<? extends NetworkMessage> messageClass) {
            this.messageClass = messageClass;
        }

        public Serializer getSerializer() {
            return serializer;
        }

        public void setSerializer(Serializer serializer) {
            this.serializer = serializer;
        }
    }

}

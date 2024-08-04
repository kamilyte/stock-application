package nl.rug.aoop.messagequeue.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import nl.rug.aoop.messagequeue.jsonAdapters.NetworkMessageJSONAdapter;

/**
 * Converts messages to json to be handled over network.
 */
public class NetworkMessage {
    @Getter
    private String header;
    @Getter
    private String body;
    private Gson gson;

    /**
     * Constructor.
     * @param header header of message
     * @param jsonString json of message
     */
    public NetworkMessage(String header, String jsonString) {
        this.header = header;
        this.body = jsonString;
        gson = new GsonBuilder().registerTypeAdapter(NetworkMessage.class, new NetworkMessageJSONAdapter()).create();
    }

    /**
     * Creates json message.
     * @param mqMessage message to be converted
     * @return json message
     */
    public NetworkMessage createPutMessage(MQMessage mqMessage) {
        return new NetworkMessage("MQPut", mqMessage.convertToJSON());
    }

    /**
     * Converts message to json.
     * @return json message
     */
    public String convertToJSON() {
        return gson.toJson(this);
    }

    /**
     * Converts json to message.
     * @param jsonString json of message
     * @return message
     */
    public NetworkMessage convertToMessage(String jsonString) {
        return gson.fromJson(jsonString, NetworkMessage.class);
    }

}

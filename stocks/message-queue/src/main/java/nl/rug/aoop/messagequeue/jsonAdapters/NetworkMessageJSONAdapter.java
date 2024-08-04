package nl.rug.aoop.messagequeue.jsonAdapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import nl.rug.aoop.messagequeue.messages.MQMessage;
import nl.rug.aoop.messagequeue.messages.NetworkMessage;

import java.io.IOException;

/**
 * Json converter for over the network.
 */
public class NetworkMessageJSONAdapter extends TypeAdapter<NetworkMessage> {
    private Gson gson = new GsonBuilder().registerTypeAdapter(MQMessage.class,
            new MQMessageJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter writer, NetworkMessage networkMessage) throws IOException {
        writer.beginObject();
        writer.name("header");
        writer.value(networkMessage.getHeader());
        writer.name("value");
        writer.value(networkMessage.getBody());
        writer.endObject();
    }

    /**
     * Reads json to convert to message for over the network.
     * @param jsonReader reads json
     * @return message to be sent over network
     * @throws IOException error in reading json
     */
    @Override
    public NetworkMessage read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        String nameOfField = null;
        String header = null;
        String value = null;

        while(jsonReader.hasNext()) {
            JsonToken token = jsonReader.peek();

            if(token.equals(JsonToken.NAME)) {
                nameOfField = jsonReader.nextName();
            }
            if ("header".equals(nameOfField)) {
                token = jsonReader.peek();
                header = jsonReader.nextString();
            }
            if ("value".equals(nameOfField)) {
                token = jsonReader.peek();
                value = jsonReader.nextString();
            }
        }
        jsonReader.endObject();
        NetworkMessage networkMessage = new NetworkMessage(header, value);
        return networkMessage;
    }

}

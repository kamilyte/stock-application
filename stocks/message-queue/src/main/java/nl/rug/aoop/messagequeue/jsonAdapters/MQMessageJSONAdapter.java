package nl.rug.aoop.messagequeue.jsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import nl.rug.aoop.messagequeue.messages.MQMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Conversion between message and json.
 */
public class MQMessageJSONAdapter extends TypeAdapter<MQMessage> {
    @Override
    public void write(JsonWriter writer, MQMessage mqMessage) throws IOException {
        writer.beginObject();
        writer.name("header");
        writer.value(mqMessage.getHeader());
        writer.name("value");
        writer.value(mqMessage.getValue());
        writer.name("timestamp");
        writer.value(mqMessage.getTimestamp().toString());
        writer.endObject();
    }

    /**
     * Reads json to message.
     * @param jsonReader reads json
     * @return message
     * @throws IOException if its null
     */
    @Override
    public MQMessage read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        String nameOfField = null;
        String header = null;
        String value = null;
        LocalDateTime time = null;
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
            if ("timestamp".equals(nameOfField)) {
                token = jsonReader.peek();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                time = LocalDateTime.parse(jsonReader.nextString(), formatter);
            }
        }
        jsonReader.endObject();
        MQMessage mqMessage = new MQMessage(header, value, time);
        return mqMessage;
    }
}
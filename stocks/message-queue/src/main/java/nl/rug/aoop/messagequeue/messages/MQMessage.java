package nl.rug.aoop.messagequeue.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.messagequeue.jsonAdapters.MQMessageJSONAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Message class.
 */
@Getter
public final class MQMessage {
    private final String header, value;
    @Setter
    private final LocalDateTime timestamp;
    private Gson gson;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Constructor for a message.
     * @param mHeader Header of a message.
     * @param mValue Value of a message.
     */
    public MQMessage(String mHeader, String mValue) {
        header = mHeader;
        value = mValue;
        timestamp = LocalDateTime.parse(LocalDateTime.now().format(formatter));
        gson = new GsonBuilder().registerTypeAdapter(MQMessage.class,
                new MQMessageJSONAdapter()).setPrettyPrinting().create();
    }

    /**
     * Constructor.
     * @param mHeader header of message
     * @param mValue body of message
     * @param time time of message
     */
    public MQMessage(String mHeader, String mValue, LocalDateTime time) {
        header = mHeader;
        value = mValue;
        timestamp = time;
        gson = new GsonBuilder().registerTypeAdapter(MQMessage.class, new MQMessageJSONAdapter()).create();
    }

    public int getTimeStampHash() {
        return getTimestamp().hashCode();
    }

    /**
     * Converts message to json.
     * @return json string
     */
    public String convertToJSON() {
        return gson.toJson(this);
    }

    /**
     * Converts json to message.
     * @param jsonString json string to be converted
     * @return message
     */
    public MQMessage convertToMessage(String jsonString) {
        return gson.fromJson(jsonString, MQMessage.class);
    }

}


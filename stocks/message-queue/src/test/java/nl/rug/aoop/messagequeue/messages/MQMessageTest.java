package nl.rug.aoop.messagequeue.messages;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MQMessageTest {
    MQMessage testMQMessage = new MQMessage("Testing Header", "Testing Value");
    LocalDateTime time = testMQMessage.getTimestamp();
    @Test
    void messageToJSONTest() {
        String jsonString = testMQMessage.convertToJSON();
        String rightJason = "{\n" +
                "  \"header\": \"Testing Header\",\n" +
                "  \"value\": \"Testing Value\",\n" +
                "  \"timestamp\": " + "\"" + time.toString() + "\"\n" +
                "}";
        assertEquals(jsonString, rightJason);
    }

    @Test
    void JSONToMessage() {
        String jsonString = "{\n" +
                "  \"header\": \"Testing Header\",\n" +
                "  \"value\": \"Testing Value\",\n" +
                "  \"timestamp\": " + "\"" + time.toString() + "\"\n" +
                "}";
        MQMessage mqMessage = new MQMessage(null, null).convertToMessage(jsonString);
        assertEquals(mqMessage.getHeader(), testMQMessage.getHeader());
        assertEquals(mqMessage.getValue(), testMQMessage.getValue());
        assertEquals(mqMessage.getTimestamp(), testMQMessage.getTimestamp());
    }

}

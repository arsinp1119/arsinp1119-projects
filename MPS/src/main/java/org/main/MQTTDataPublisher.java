package org.main;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.google.gson.Gson;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.logging.*;

public class MQTTDataPublisher {

    private static final String BROKER = "tcp://192.168.200.142:1883";
    private static final String CLIENT_ID = "2/SUR/201192";
    private static final String TOPIC = "display/commands/2/SUR/245339";
    private static final int QOS = 1;
    private static final long UPDATE_INTERVAL = 2 * 60 * 1000; // 2 minutes in milliseconds
    private static final String LOG_FILE = "C:\\Users\\service_lp\\Documents\\TFLPOC\\Simulator\\MPS\\logs\\mqtt_publisher.log";

    private static final Gson gson = new Gson();
    private static final Random random = new Random();

    private static final Logger logger = Logger.getLogger(MQTTDataPublisher.class.getName());

    private static final List<String> destinations = new ArrayList<>();
    static {
        destinations.add("Stratford");
        destinations.add("Liverpool Street");
        destinations.add("Aldgate");
        destinations.add("Waterloo");
        destinations.add("Canning Town");
        destinations.add("Victoria");
        destinations.add("Moorgate");
        destinations.add("Old Kent Road");
        destinations.add("Euston");
        destinations.add("Blackwall");
        destinations.add("London Bridge");
        destinations.add("Paddington");
    }

    public static void main(String[] args) throws Exception {
        FileHandler fh = new FileHandler(LOG_FILE,true);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord lr) {
                TimeZone tz = TimeZone.getTimeZone("UTC"); // UTC time zone
                java.time.ZonedDateTime zdt = java.time.ZonedDateTime.ofInstant(
                        java.time.Instant.ofEpochMilli(lr.getMillis()).minus(6 * 60 + 10, ChronoUnit.SECONDS), // Subtracting 6 minutes and 10 seconds
                        tz.toZoneId()
                );
                return String.format(
                        "[%s] %s - %s%n",
                        lr.getLevel(),
                        zdt,
                        lr.getMessage()
                );
            }
        };
        fh.setFormatter(formatter);

        MqttClient client = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        client.connect(connOpts);
        logger.info("Connected to MQTT broker: " + BROKER);

        while (true) {
            String jsonData = generateRandomUpdate();
            MqttMessage message = new MqttMessage(jsonData.getBytes());
            message.setQos(QOS);
            client.publish(TOPIC, message);
            logger.info("Published message: " + jsonData);
            Thread.sleep(UPDATE_INTERVAL);
        }
    }

    private static String generateRandomUpdate() {
        String destination1 = destinations.get(random.nextInt(destinations.size()));
        String destination2 = destinations.get(random.nextInt(destinations.size()));
        String destination3 = destinations.get(random.nextInt(destinations.size()));
        String destination4 = destinations.get(random.nextInt(destinations.size()));
        TripUpdate[] updates = {
            new TripUpdate("101", destination1, Integer.toString(random.nextInt(60))),
            new TripUpdate("102", destination2, Integer.toString(random.nextInt(60))),
            new TripUpdate("103", destination3, Integer.toString(random.nextInt(60))),
            new TripUpdate("104", destination4, Integer.toString(random.nextInt(60)))
        };
        return gson.toJson(updates);
    }

    static class TripUpdate {
        String tripId;
        String destination;
        String time;

        public TripUpdate(String tripId, String destination, String time) {
            this.tripId = tripId;
            this.destination = destination;
            this.time = time;
        }
    }
}



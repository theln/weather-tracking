package com.weather.sensor;

import javax.jms.*;
import com.weather.beans.WeatherInfo;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HaNoiWeatherSensor implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(HaNoiWeatherSensor.class);

    private ConnectionFactory factory;
    private static Connection connection;
    private static Session session;
    private static Destination destination;
    private static MessageProducer producer;
    private String queueName;

    public HaNoiWeatherSensor(ConnectionFactory factory, String queueName) {
        this.factory = factory;
        this.queueName = queueName;
    }

    public void run() {
        while (true) {
            sendMessage();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMessage() {
        try {
            WeatherInfo weatherInfo = new WeatherInfo();
            weatherInfo.setSensorName("Ha Noi weather sensor");
            weatherInfo.setTemperature(Utils.getRandomValue(22.5f, 24.9f));
            weatherInfo.setHumidity(Utils.getRandomValue(50.0f, 100.0f));

            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(weatherInfo);
            producer.send(objectMessage);

            logger.info("Sent: {}", weatherInfo.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void startJms() {
        try {
            factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}

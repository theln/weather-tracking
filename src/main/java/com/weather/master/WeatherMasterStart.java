package com.weather.master;

import javax.jms.*;
import com.weather.beans.WeatherInfo;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class WeatherMasterStart {
    private static Logger logger = LoggerFactory.getLogger(WeatherMasterStart.class);

    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;
    private String queueName;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("weather-tracking-spring.xml");
        WeatherMasterStart receiver = (WeatherMasterStart) applicationContext.getBean("weather.Master");
        receiver.startJms();
        while (true) {
            receiver.receiveMessage();
        }
    }

    public void receiveMessage() {
        try {
            Message message = consumer.receive();
            if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                WeatherInfo weatherInfo = (WeatherInfo) objectMessage.getObject();

                logger.info("Received: {}", weatherInfo);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void startJms() {
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            consumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void setFactory(ConnectionFactory factory) {
        this.factory = factory;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}

package com.weather.sensor;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import javax.jms.ConnectionFactory;

public class WeatherSensorStart {
    private String queueName;
    private ConnectionFactory factory;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("weather-tracking-spring.xml");
        WeatherSensorStart weatherSensor = (WeatherSensorStart) applicationContext.getBean("weather.Sensor");

        System.out.println("Start sensor Ha Noi..");
        HaNoiWeatherSensor haNoi = new HaNoiWeatherSensor(weatherSensor.getFactory(), weatherSensor.getQueueName());
        haNoi.startJms();
        new Thread(haNoi).run();
        System.out.println("Start sensor Ha Noi done!");
        //
        System.out.println("Start sensor Hai Phong..");
        HaiPhongWeatherSensor haiPhong = new HaiPhongWeatherSensor(weatherSensor.getFactory(), weatherSensor.getQueueName());
        haiPhong.startJms();
        new Thread(haiPhong).run();
        System.out.println("Start sensor Hai Phong done!");

        //
        System.out.println("Start sensor Ho Chi Minh City..");
        HoChiMinhCityWeatherSensor hoChiMinhCity = new HoChiMinhCityWeatherSensor(weatherSensor.getFactory(), weatherSensor.getQueueName());
        hoChiMinhCity.startJms();
        new Thread(hoChiMinhCity).run();
        System.out.println("Start sensor Ho Chi Minh City done!");
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public ConnectionFactory getFactory() {
        return factory;
    }

    public void setFactory(ConnectionFactory factory) {
        this.factory = factory;
    }
}

package org.myeyes.ai.agentstools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTools {

    @Tool(description = "fetches current weather Condition for given city")
    public String getWeather(String city) {
        System.out.println("getWeather:---------- ");
        return "The Weather in the " + city + " is 40 degree with light winds";
    }

    @Tool(description = "Provides weather advice based on the supplied weather condition.")
    public String getWeatherAdvice(String weather) {
        System.out.println("getWeather Advice:---------- ");
        if (weather.toLowerCase().contains("rain")) {
            return "Carry an umbrella if it's raining";
        }
        if (weather.toLowerCase().contains("cold")) {
            return "Wear warm clothes -->  it's cold.";
        }
        return "Weather Looks Fine!!!!! enjoy your day";
    }
}

package com.mantovani.park_api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.TimeZone;

@Configurable
public class SpringTimezoneConfig {

    @PostConstruct
    public void timezoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

}

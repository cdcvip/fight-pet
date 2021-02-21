package com.chone.fightpet.config;

import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Create 2020-12-30 17:17
 *
 * @author chone
 */
@Slf4j
//@Configuration
//@ConfigurationProperties(prefix = "fight")
public class FightConfiguration {

    private final static String URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=";

    /**
     * header
     */
    private Map<String, String> header;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getUrl() {
        return URL;
    }
}

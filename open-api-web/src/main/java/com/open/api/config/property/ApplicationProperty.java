package com.open.api.config.property;

import com.open.api.model.AppIdModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "open.appId")
public class ApplicationProperty {
    /**
     * appId,AppIdModel
     */
    private Map<String, AppIdModel> appKeyMap;
}
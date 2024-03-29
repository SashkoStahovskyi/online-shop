package com.stahovskyi.onlineshop.configuration;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static final String LOCAL_PROPERTIES_FILE_PATH = "configs/application.properties";

    public static Properties getLocalProperties() {
        return readLocalProperties(LOCAL_PROPERTIES_FILE_PATH);
    }

    private static Properties readLocalProperties(String appPropertiesFilePath) {
        return readLocalProperties(PropertiesReader.class.getClassLoader().getResourceAsStream(appPropertiesFilePath));
    }

    private static Properties readLocalProperties(InputStream appPropertiesInputStream) {
        Properties appProperties = new Properties();

        try (InputStream inputStream = appPropertiesInputStream) {
            appProperties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Cannot read application properties file", e);
        }
        return appProperties;
    }

}

package com.stahovskyi.onlineshop.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static final String LOCAL_PROPERTIES_FILE_PATH = "configs/application.properties";

    private static final String COOKIE_AGE_NAME = "cookie.maxAge";

    public Properties getLocalProperties() {
        return readLocalProperties(LOCAL_PROPERTIES_FILE_PATH);
    }

    static Properties readLocalProperties(String appPropertiesFilePath) {
        return readLocalProperties(PropertiesReader.class.getClassLoader().getResourceAsStream(appPropertiesFilePath));
    }

    static Properties readLocalProperties(InputStream appPropertiesInputStream) {
        Properties appProperties = new Properties();

        try (InputStream inputStream = appPropertiesInputStream) {
            appProperties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Cannot read application properties file", e);
        }
        return appProperties;
    }

    public static int getCookieAge() {
        Properties properties = new Properties();
        int propertyValue = 0;

        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(LOCAL_PROPERTIES_FILE_PATH)) {
            properties.load(inputStream);
            propertyValue = Integer.parseInt(properties.getProperty(COOKIE_AGE_NAME));

        } catch (IOException e) {
            throw new RuntimeException("Cannot read application properties file", e);
        }
        return propertyValue;
    }

}

package com.stahovskyi.onlineshop.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
   // private final String path;
    private Properties properties;

    private static final String LOCAL_PROPERTIES_FILE_PATH = "configs/app.properties";

    public Properties getLocalProperties() {
        return readLocalProperties(LOCAL_PROPERTIES_FILE_PATH);
    }

    static Properties readLocalProperties(String appPropertiesFilePath) {
        return readLocalProperties(PropertiesReader.class.getClassLoader().getResourceAsStream(appPropertiesFilePath));
    }

    static Properties readLocalProperties(InputStream appPropertiesInputStream) {
        Properties appProperties = new Properties();
        try (InputStream in = appPropertiesInputStream) {
            appProperties.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read application properties file", e);
        }
        return appProperties;
    }

  /*  public PropertiesReader(String path) {
        this.path = path;
        properties = readProperties();

    }

    public Properties getProperties() {

        return new Properties(properties);
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        properties.
        // read logic

        return properties;
    }*/
}

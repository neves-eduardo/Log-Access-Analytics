package com.neves_eduardo.logaccesssanalytics.config;

import com.neves_eduardo.logaccesssanalytics.exception.CannotLoadConfigsException;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class PropertiesReader {
    private static final String CONFIG_FILE_PATH = System.getProperty("configpath");
    private Properties properties;

    public PropertiesReader() {
        this.properties = new Properties();
    }

    public String loadProperty(String property) {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new CannotLoadConfigsException("Can not load Configs");
        }
        return properties.getProperty(property);
    }
}

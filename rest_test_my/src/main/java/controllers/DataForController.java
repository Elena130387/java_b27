package controllers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataForController {
  public final Properties properties = new Properties();

  public DataForController() {
    try {
      properties.load(new FileReader(new File("src/main/resources/testApi.properties")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

/**
 * Note to self on different ways to interact with properties files.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Mike Lowe
 */
public final class PropertiesExamples {

  // don't want instances
  private PropertiesExamples() {
  }

  /*
   * There are two ways to read and write properties files using Java.
   * With plain text files and XML files.
   */

  public static void usingPlainText() throws IOException {
    // load properties
    final File defaultTxt = new File("default.properties");
    final Properties defaultProperties = loadDefaultPropertiesFromPlainText(defaultTxt);
    final File userTxt = new File("user.properties");
    final Properties userProperties = loadUserPropertiesFromPlainText(userTxt, defaultProperties);

    String name = userProperties.getProperty("name");

    // have we run it before?
    if (userProperties.containsKey("runBefore")) {
      System.out.println("Welcome back " + name);
      return;
    }

    // we haven't so get input from user
    System.out.println("You have not run this before");
    System.out.println("The default name is " + name);
    System.out.print("Enter new name: ");
    final Scanner scanner = new Scanner(System.in);
    name = scanner.nextLine();
    scanner.close();
    System.out.println("Hello " + name);
    userProperties.put("name", name);
    userProperties.put("runBefore", "true");

    // update the user properties file
    writePropertiesToPlainText(userProperties, userTxt);
  }

  private static Properties loadDefaultPropertiesFromPlainText(final File file) throws IOException {
    final Properties properties = new Properties();
    try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
      properties.load(inputStream);
    }
    return properties;
  }

  private static Properties loadUserPropertiesFromPlainText(final File file, final Properties defaultProperties)
      throws IOException {
    final Properties properties = new Properties(defaultProperties);
    if (file.exists()) {
      try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
        properties.load(inputStream);
      }
    }
    return properties;
  }

  private static void writePropertiesToPlainText(final Properties properties, final File file) throws IOException {
    try (final OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
      properties.store(outputStream, null);
    }
  }

  public static void usingXML() throws IOException {
    //load properties
    final File defaultXML = new File("default.properties.xml");
    final Properties defaultProperties = loadDefaultPropertiesFromXML(defaultXML);
    final File userXML = new File("user.properties.xml");
    final Properties userProperties = loadUserPropertiesFromXML(userXML, defaultProperties);

    String name = userProperties.getProperty("name");

    // have we run it before?
    if (userProperties.containsKey("runBefore")) {
      System.out.println("Welcome back " + name);
      return;
    }

    // we haven't so get input from user
    System.out.println("You have not run this before");
    System.out.println("The default name is " + name);
    System.out.print("Enter new name: ");
    final Scanner scanner = new Scanner(System.in);
    name = scanner.nextLine();
    scanner.close();
    System.out.println("Hello " + name);
    userProperties.put("name", name);
    userProperties.put("runBefore", "true");

    // update the user properties file
    writePropertiesToXML(userProperties, userXML);
  }


  private static Properties loadDefaultPropertiesFromXML(final File file) throws IOException {
    final Properties properties = new Properties();
    try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
      properties.loadFromXML(inputStream);
    }
    return properties;
  }

  private static Properties loadUserPropertiesFromXML(final File file, final Properties defaultProperties)
      throws IOException {
    final Properties properties = new Properties(defaultProperties);
    if (file.exists()) {
      try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
        properties.loadFromXML(inputStream);
      }
    }
    return properties;
  }

  private static void writePropertiesToXML(final Properties properties, final File file) throws IOException {
    try (final OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
      properties.storeToXML(outputStream, null);
    }
  }

}

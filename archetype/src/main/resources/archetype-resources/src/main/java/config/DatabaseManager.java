#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseManager {

  private static String url;
  private static String user;
  private static String password;

  static {
    try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("h2config.properties")) {
      Properties properties = new Properties();

      if (input == null) {
        log.error("No se encontró h2config.properties en el classpath.");
        throw new RuntimeException("Lo siento, no se pudo encontrar h2config.properties en la carpeta resources.");
      }

      properties.load(input);
      url = properties.getProperty("db.url");
      user = properties.getProperty("db.user");
      password = properties.getProperty("db.password");
      String driver = properties.getProperty("db.driver");

      log.debug("Cargando configuración de BD desde h2config.properties para url={}", url);
      Class.forName(driver);
      log.info("Driver de base de datos cargado correctamente: {}", driver);

    } catch (Exception ex) {
      log.error("Error inicializando la base de datos.", ex);
      throw new RuntimeException("No se pudo inicializar la base de datos", ex);
    }
  }

  public static Connection getConnection() throws SQLException {
    try {
      log.debug("Solicitando conexión a la base de datos: {}", url);
      Connection connection = DriverManager.getConnection(url, user, password);
      log.debug("Conexión obtenida exitosamente para usuario: {}", user);
      return connection;
    } catch (SQLException ex) {
      log.error("Error al abrir conexión con la base de datos (url={}).", url, ex);
      throw ex;
    }
  }
}
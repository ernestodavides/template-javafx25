#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.net.URL;
import java.sql.Connection;

import ${package}.config.DatabaseManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void start(Stage stage) {
    String mensaje = "¡Proyecto Base Listo!";

    URL url = App.class.getClassLoader().getResource("app.png");
    if (url != null) {
      stage.getIcons().add(new Image(url.toExternalForm()));
      log.debug("Ícono de aplicación cargado desde recurso: {}", url.toExternalForm());
    } else {
      log.warn("No se encontró el recurso de ícono 'app.png' en el classpath.");
    }

    try (Connection conn = DatabaseManager.getConnection()) {
      mensaje += "${symbol_escape}nH2 conectado usando h2config.properties.";
      log.info("Conexión a la base de datos H2 establecida.");
    } catch (Exception e) {
      log.error("No se pudo conectar a la base de datos H2.", e);
      mensaje += "${symbol_escape}nError: " + e.getMessage();
    }

    Label label = new Label(mensaje);

    Scene scene = new Scene(new StackPane(label), 600, 400);
    stage.setScene(scene);
    stage.setTitle("Nuevo Proyecto");

    stage.show();
    log.info("Ventana principal mostrada.");
  }

  public static void main(String[] args) {
    log.info("Iniciando aplicación.");
    launch();
  }
}
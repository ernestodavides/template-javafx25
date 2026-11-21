#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
module ${package} {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires java.logging;

  requires static lombok;
  requires org.slf4j;

  opens ${package} to javafx.fxml;

  exports ${package};
}
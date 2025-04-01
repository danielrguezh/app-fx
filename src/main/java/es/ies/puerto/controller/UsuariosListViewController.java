package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.UsuarioEntity;
import es.ies.puerto.model.UsuarioServiceModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class UsuariosListViewController extends AbstractController{
    @FXML
    private Text textListaUsuario;

    @FXML
    private Button buttonVolverAtras;

    @FXML
    private ListView<UsuarioEntity> listViewUsuarios;

    /**
     * Metodo de inicializacion de la interfaz
     */
    @FXML
    public void initialize() {
        cambiarIdioma();
        configurarListView();
        cargarUsuarios();
    }

    /**
     * Configura la visualizacion de la lista de usuarios y
     * define como se mostraran los elementos en la ListView
     */
    private void configurarListView() {
        listViewUsuarios.setCellFactory(param -> new ListCell<UsuarioEntity>() {
        @Override
        protected void updateItem(UsuarioEntity usuario, boolean empty) {
            super.updateItem(usuario, empty);
            if (empty || usuario == null) {
                setText(null);
                return;
            }
            String idRow = ConfigManager.ConfigProperties.getProperty("idRow");
            String usuarioRow = ConfigManager.ConfigProperties.getProperty("usuarioRow");
            String emailRow = ConfigManager.ConfigProperties.getProperty("emailRow");
            String formato = ""+idRow+": %s\n"+usuarioRow+": %s\n"+emailRow+": %s";
            String texto = String.format(formato, usuario.getId(), usuario.getUsuario(), usuario.getEmail());
            setText(texto);
            }
        });
    }

    /**
     * Metodo que carga los usuarios desde la base de datos y los muestra en la ListView
     */
    private void cargarUsuarios() {
        try {
            UsuarioServiceModel service = getUsuarioServiceModel();
            ArrayList<UsuarioEntity> listaUsuarios = service.obtenerUsuarios();
            ObservableList<UsuarioEntity> usuarios = FXCollections.observableArrayList(listaUsuarios);
            listViewUsuarios.setItems(usuarios);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de clic en el boton de volver atras
     * Redirige a la pantalla de inicio de sesion (login)
     */
    @FXML
    protected void onVolverAtrasClick() {
        String tituloPantalla = ConfigManager.ConfigProperties.getProperty("loginTitle");
        mostrarPantalla(buttonVolverAtras, "login.fxml", tituloPantalla);
    }
  }
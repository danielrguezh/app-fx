package es.ies.puerto.controller;


import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.UsuarioEntity;
import es.ies.puerto.servicio.UsuarioServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class LoginController extends AbstractController {
    private final String pathFichero="src/main/resources/";
    private final String ficheroStr= "idioma-";

    UsuarioServicio usuarioServicioJson;
    
    /**
     * Constructor por defecto
     */
    public LoginController() {
        usuarioServicioJson = new UsuarioServicio();
    }

    @FXML
    private ComboBox comboIdioma;

    @FXML
    private TextField textFieldUsuarioEmail;
    
    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private Text textMensaje;

    @FXML
    private Text textUsuarioEmail;

    @FXML
    private Text textContrasenia;

    @FXML
    private Button openAceptarButton;

    @FXML
    private Button openRegistrarButton;

    @FXML
    private Button openListarUsuariosButton;

    @FXML
    private Button buttonRecuperarContrasenia;

    /**
     * Metodo de inicializacion de la interfaz
     */
    @FXML
    public void initialize() {
        comboIdioma.getItems().add("es");
        comboIdioma.getItems().add("en");
        comboIdioma.getItems().add("fr");
        String idioma = ConfigManager.ConfigProperties.getIdiomaActual();
        comboIdioma.setValue(idioma);
        cargarIdioma(idioma);
        cambiarIdioma();
    }

    /**
     * Actualiza el idioma en la configuracion global
     */
    @FXML
    protected void seleccionarIdiomaClick() {
        String idioma = comboIdioma.getValue().toString();
        ConfigManager.ConfigProperties.setIdiomaActual(idioma);
        cargarIdioma(idioma);
        cambiarIdioma();
        actualizarTituloVentana();
    }

    /**
     * Carga el archivo properties del idioma
     * @param idioma
     */
    private void cargarIdioma(String idioma) {
        String path = pathFichero+ficheroStr+idioma+".properties";
        ConfigManager.ConfigProperties.setPath(path);
    }

    /**
     * Actualiza dinamicamente el titulo de la ventana principal
     */
    public void actualizarTituloVentana() {
        Stage stage = (Stage) textUsuarioEmail.getScene().getWindow();
        String titulo = ConfigManager.ConfigProperties.getProperty("loginTitle");
        stage.setTitle(titulo);
    }

    /**
     * Maneja el evento de clic en el boton de inicio de sesion
     * Valida las credenciales del usuario y muestra mensajes de error si es necesario
     * Si las credenciales son correctas, se redirige a la pantalla de perfil
     */
    @FXML
    protected void onLoginButtonClick() {
        if (textFieldUsuarioEmail == null || textFieldUsuarioEmail.getText().isEmpty() || 
            textFieldPassword == null || textFieldPassword.getText().isEmpty() ) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorCredencialesVacios"));
            return;
        }
        UsuarioEntity usuario = getUsuarioServiceModel().obtenerUsuarioPorEmail(textFieldUsuarioEmail.getText());
        if (usuario == null) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorUsuarioNoEncontrado"));
            return;
        }

         boolean passwordCorrecta = textFieldPassword.getText().equals(usuario.getPassword());
        if (!passwordCorrecta) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorContraseniaIncorrecta"));
            return;
        }
        
        String tituloPantalla = ConfigManager.ConfigProperties.getProperty("profileTitle");
        mostrarPantallaMasUsusarios(openAceptarButton, "profile.fxml", tituloPantalla, usuario);

        /** Codigo en caso del json
        boolean passwordCorrecta = BCrypt.checkpw(textFieldPassword.getText(), usuario.getPassword());

        UsuarioEntity usuario = usuarioServicio.buscarUsuarioPorCriterio(textFieldUsuario.getText(), UsuarioEntity::getUsuario);
        if (usuario == null) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorUsuarioNoEncontrado"));
            return;
        }
        boolean passwordCorrecta = BCrypt.checkpw(textFieldPassword.getText(), usuario.getPassword());
        if (!passwordCorrecta) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorContraseniaIncorrecta"));
            return;
        }
        */
    }

    /**
     * Maneja el evento de clic en el boton de registro
     * Redirige a la pantalla de registro
     */
    @FXML
    protected void openRegistrarClick() {
        String tituloPantalla = ConfigManager.ConfigProperties.getProperty("registroTitle");
        mostrarPantalla(openRegistrarButton, "registro.fxml", tituloPantalla);
    }

    /**
     * Maneja el evento de clic en el boton de listar usuarios
     * Redirige a la pantalla de de lista de usuarios
     */
    @FXML
    protected void openListarUsuariosClick() {
        String tituloPantalla = ConfigManager.ConfigProperties.getProperty("usuarioTitle");
        mostrarPantalla(openRegistrarButton, "usuariosListView.fxml", tituloPantalla);
    }

    /**
     * Maneja el evento de clic en el boton de recuperar contrasenia
     * Redirige a la pantalla de recuperacion de contrasenia
     */
    @FXML
    protected void openRecuperarContraseniaClick() {
        String tituloPantalla = ConfigManager.ConfigProperties.getProperty("passwordTitle");
        mostrarPantalla(buttonRecuperarContrasenia, "password.fxml", tituloPantalla);
    }
}
package es.ies.puerto.model;

import java.util.Objects;



/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class UsuarioEntity {
    private int id;
    private String usuario;
    private String password;
    private String nombre;
    private String email;

    public UsuarioEntity() {
    }
    public UsuarioEntity id(int id) {
        setId(id);
        return this;
    }

    public UsuarioEntity usuario(String usuario) {
        setUsuario(usuario);
        return this;
    }

    public UsuarioEntity email(String email) {
        setEmail(email);
        return this;
    }

    public UsuarioEntity(String usuario, String password, String email) {
        this.usuario = usuario;
        this.password = password;
        this.email = email;
    }

    public UsuarioEntity(int id, String usuario, String password, String email) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.email = email;
    }

    public UsuarioEntity(int id, String usuario, String password, String nombre, String email) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UsuarioEntity)) {
            return false;
        }
        UsuarioEntity usuarioEntity = (UsuarioEntity) o;
        return  Objects.equals(email, usuarioEntity.email);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", password='" + getPassword() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
    
}  
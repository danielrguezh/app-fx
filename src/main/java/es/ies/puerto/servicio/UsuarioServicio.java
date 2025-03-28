package es.ies.puerto.servicio;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.ies.puerto.model.UsuarioEntity;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class UsuarioServicio {
    ObjectMapper objectMapper;
    String path = "src/main/resources/es/ies/puerto/json/usuarios.json";
    File file;
    Set<UsuarioEntity> usuarios;

    /**
     * Constructor por defecto
     */
    public UsuarioServicio() {
        usuarios = new HashSet<>();
        objectMapper = new ObjectMapper();
        file = new File(path);
        loadAll();
    }

    /**
     * Metodo que carga todos los usuarios del fichero json
     * @return lista de usuarios
     */
    public Set<UsuarioEntity> loadAll() {
        if (!file.exists()) {
            saveFile(file, usuarios);
            return usuarios;
        }
        try {
            if (file.length() > 0) {
                usuarios = objectMapper.readValue(file, new TypeReference<Set<UsuarioEntity>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    /**
     * Metodo para sobreescribir el fichero
     * @param file json
     * @param usuarios
     */
    public void saveFile(File file, Set<UsuarioEntity> usuarios) {
        try {
            objectMapper.writeValue(file, usuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para agregar un nuevo usuario
     * @param usuario  a agregar
     * @return true/false
     */
    public boolean add(UsuarioEntity usuario) {
        if (usuario == null) {
            return false;
        }
        boolean added = usuarios.add(usuario);
        if (added) {
            saveFile(file, usuarios);
        }
        return added;
    }

    /**
     * Metodo para buscar un usuario dado un criterio de busqueda
     * @param valor a buscar
     * @param criterio de busqueda
     * @return usuario
     */
    public UsuarioEntity buscarUsuarioPorCriterio(String valor, Function<UsuarioEntity, String> criterio) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        for (UsuarioEntity usuario : usuarios) {
            if (criterio.apply(usuario).equals(valor)) {
                return usuario;
            }
        }
        return null;
    }
}

package com.juaachuc.todo_list.service;

import com.juaachuc.todo_list.model.Usuario;
import com.juaachuc.todo_list.model.Tarea;
import com.juaachuc.todo_list.repository.UsuarioRepository;
import com.juaachuc.todo_list.repository.TareaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaService(TareaRepository tareaRepository, UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Usuario no autenticado");
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal instanceof String) {
            username = (String) principal; // Spring Security a veces devuelve solo el username como String
        } else {
            throw new UsernameNotFoundException("No se pudo determinar el usuario autenticado");
        }

        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public List<Tarea> obtenerTodas() {
        Usuario usuario = obtenerUsuarioAutenticado();
        return tareaRepository.findByUsuario(usuario);
    }

    public Optional<Tarea> obtenerPorId(Long id) {
        Usuario usuario = obtenerUsuarioAutenticado();
        return tareaRepository.findByIdAndUsuario(id, usuario);
    }

    public Tarea guardar(Tarea tarea) {
        Usuario usuario = obtenerUsuarioAutenticado();
        tarea.setUsuario(usuario);
        return tareaRepository.save(tarea);
    }

    public Tarea actualizar(Long id, Tarea tareaActualizada) {
        Usuario usuario = obtenerUsuarioAutenticado();
        return tareaRepository.findByIdAndUsuario(id, usuario).map(tarea -> {
            tarea.setTitulo(tareaActualizada.getTitulo());
            tarea.setDescripcion(tareaActualizada.getDescripcion());
            tarea.setCompletada(tareaActualizada.isCompletada());

            return tareaRepository.save(tarea);
        }).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public Tarea actualizarParcial(Long id, Map<String, Object> campos) {
        Usuario usuario = obtenerUsuarioAutenticado();
        return tareaRepository.findByIdAndUsuario(id, usuario).map(tarea -> {
            if (campos.containsKey("titulo")) {
                tarea.setTitulo((String) campos.get("titulo"));
            }

            if (campos.containsKey("descripcion")) {
                tarea.setDescripcion((String) campos.get("descripcion"));
            }

            if (campos.containsKey("completada")) {
                tarea.setCompletada((Boolean) campos.get("completada"));
            }
            return tareaRepository.save(tarea);
        }).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public void eliminar(Long id) {
        Usuario usuario = obtenerUsuarioAutenticado();
        tareaRepository.findByIdAndUsuario(id, usuario)
                .ifPresent(tareaRepository::delete);
    }
}

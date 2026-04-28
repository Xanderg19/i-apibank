package proyecto_practica.proyecto_practica.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyecto_practica.proyecto_practica.model.Usuario;
import proyecto_practica.proyecto_practica.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario crearUser(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }

}

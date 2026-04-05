package proyecto_practica.proyecto_practica.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proyecto_practica.proyecto_practica.model.Usuario;
import proyecto_practica.proyecto_practica.repository.UsuarioRepository;
import proyecto_practica.proyecto_practica.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController{

    private final UsuarioService usuarioService;

    @PostMapping
    public Usuario guardar(@Valid @RequestBody Usuario usuario){
        return usuarioService.crearUser(usuario);
    }

}

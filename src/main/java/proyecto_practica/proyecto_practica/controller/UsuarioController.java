package proyecto_practica.proyecto_practica.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proyecto_practica.proyecto_practica.model.Usuario;
import proyecto_practica.proyecto_practica.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController{

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario guardar(@Valid @RequestBody Usuario usuario){
        return usuarioService.crearUser(usuario);
    }

}

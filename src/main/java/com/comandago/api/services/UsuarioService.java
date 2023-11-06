package com.comandago.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.comandago.api.dtos.UsuarioDTO;
import com.comandago.api.models.Usuario;
import com.comandago.api.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    final UsuarioRepository usuarioRepository;

    UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> users =  usuarioRepository.findAll();
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for(Usuario u : users){
            UsuarioDTO dto = new UsuarioDTO(u);
            usuarios.add(dto);
        }
        return usuarios;
    }

    public UsuarioDTO obterUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        UsuarioDTO dto;
        if(usuarioOptional.isPresent()){
            dto = new UsuarioDTO(usuarioOptional.get());
            return dto;
        } 
        else return null;
        //return usuarioOptional.orElse(null);
    }

    public Usuario criarUsuario(Usuario usuario) {
        if(usuario != null)
            return usuarioRepository.save(usuario);
        return null;
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Optional<Usuario> usuarioExistenteOptional = usuarioRepository.findById(id);
        if (usuarioExistenteOptional.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOptional.get();
            if(usuario.getNome() != null)
                usuarioExistente.setNome(usuario.getNome());

            if(usuario.getLogin() != null){
                usuarioExistente.setLogin(usuario.getLogin());
                // Usuario user = usuarioRepository.usuarioFindByLogin(usuario.getLogin());
                // if(user.getId() == id){
                // }
            }

            if(usuario.getSenha() != null){
                String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
                usuarioExistente.setSenha(encryptedPassword);
            }

            if(usuario.getAtribuicao() != null)
                usuarioExistente.setAtribuicao(usuario.getAtribuicao());

            usuarioExistente.setEstaAtivo(usuario.isEnabled());

            return usuarioRepository.save(usuarioExistente);
        } else {
            return null;
        }
    }

    public boolean excluirUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

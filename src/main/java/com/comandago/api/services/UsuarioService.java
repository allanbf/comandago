package com.comandago.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.comandago.api.dtos.RegisterDTO;
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
        if(usuario != null){
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
            usuario.setSenha(encryptedPassword);
            return usuarioRepository.save(usuario);
        }
        return null;
    }


    public boolean atualizarUsuario(Long id, RegisterDTO usuarioAtualizado){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setNome(usuarioAtualizado.nome());
            usuario.setLogin(usuarioAtualizado.login());
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioAtualizado.senha());
            usuario.setSenha(encryptedPassword);
            usuario.setAtribuicao(usuarioAtualizado.atribuicao());
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public boolean excluirUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

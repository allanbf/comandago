package com.comandago.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.comandago.api.models.Usuario;
import com.comandago.api.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    final UsuarioRepository usuarioRepository;

    UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obterUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.orElse(null);
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
            usuarioExistente.setNome(usuario.getNome());
            usuarioExistente.setLogin(usuario.getLogin());
            usuarioExistente.setSenha(usuario.getSenha());
            usuarioExistente.setAtribuicao(usuario.getAtribuicao());
            usuarioExistente.setEstaAtivo(usuario.isEstaAtivo());
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

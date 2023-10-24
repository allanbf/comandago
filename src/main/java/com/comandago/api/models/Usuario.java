package com.comandago.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.comandago.api.enums.AtribuicaoUsuarioEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank
    @Column(nullable = false)
    private String senha;

    @Enumerated
    @Column(nullable = false)
    private AtribuicaoUsuarioEnum atribuicao;

    @Column(nullable = false)
    private boolean estaAtivo = true;

    public Usuario(Usuario usuario) {
        this.id = usuario.id;
        this.nome = usuario.nome;
        this.login = usuario.login;
        this.senha = usuario.senha;
        this.atribuicao = usuario.atribuicao;
        this.estaAtivo = usuario.estaAtivo;
    }

}

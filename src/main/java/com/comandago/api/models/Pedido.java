package com.comandago.api.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.comandago.api.enums.EstadoPedidoEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mesaId")
    private Mesa mesa;

    @Enumerated
    @Column(nullable = false)
    private EstadoPedidoEnum estado = EstadoPedidoEnum.PENDENTE;

    @JsonManagedReference
    @OneToMany(mappedBy = "pedido")
    private List<PedidosCardapio> itens;
}

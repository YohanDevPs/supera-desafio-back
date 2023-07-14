package br.com.banco.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transferencia")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_transferencia", nullable = false)
    private Timestamp transferDate;
    @Column(name = "valor", nullable = false)
    private BigDecimal amount;
    @Column(name = "tipo", nullable = false)
    private String type;
    @Column(name = "nome_operador_transacao")
    private String transactionOperatorName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Account account;
}
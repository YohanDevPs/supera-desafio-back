package br.com.banco.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "conta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
    private Long id;
    @Column(name = "nome_responsavel", nullable = false)
    private String responsibleName;
}
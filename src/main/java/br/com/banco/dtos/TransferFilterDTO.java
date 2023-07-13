package br.com.banco.dtos;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransferFilterDTO {
    private Long idAccount;
    private String transactionOperatorName;
    private Timestamp startDate;
    private Timestamp endDate;
}

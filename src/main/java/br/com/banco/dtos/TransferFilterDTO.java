package br.com.banco.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferFilterDTO {
    private Long idAccount;
    private String transactionOperatorName;
    private Timestamp startDate;
    private Timestamp endDate;
}

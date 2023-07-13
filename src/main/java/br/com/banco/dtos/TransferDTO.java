package br.com.banco.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class TransferDTO {
    private Long id;
    private Timestamp transferDate;
    private BigDecimal amount;
    private String type;
    private String transactionOperatorName;
}

package br.com.banco.services;

import br.com.banco.dtos.TransferDTO;

import java.sql.Timestamp;
import java.util.List;

public interface TransferService {
    List<TransferDTO> findAllByAccountIdAndDates(Long idAccount, String transactionOperatorName, Timestamp startDate, Timestamp endDate);
}

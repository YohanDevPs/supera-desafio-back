package br.com.banco.services;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.entities.Transfer;
import br.com.banco.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static br.com.banco.mapper.UtilModelMapper.parseListObjects;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository repository;

    @Override
    public List<TransferDTO> findAllByAccountIdAndDates(Long idAccount,
                                                        String transactionOperatorName,
                                                        Timestamp startDate,
                                                        Timestamp endDate) {
        List<Transfer> transfers = repository
                .findByAccountIdAndTransactionOperatorNameAndTransferDateBetween(
                        idAccount, transactionOperatorName ,startDate, endDate
                );
        return parseListObjects(transfers, TransferDTO.class);
    }
}

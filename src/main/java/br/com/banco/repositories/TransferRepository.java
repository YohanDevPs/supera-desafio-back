package br.com.banco.repositories;

import br.com.banco.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    List<Transfer> findByAccountIdAndTransactionOperatorNameAndTransferDateBetween(
            Long accountId, String transactionOperatorName, Timestamp startTime, Timestamp endTime);
}

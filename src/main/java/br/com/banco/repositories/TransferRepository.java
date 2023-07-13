package br.com.banco.repositories;

import br.com.banco.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT t FROM Transfer t " +
            "WHERE t.account.id = :accountId " +
            "AND (COALESCE(:transactionOperatorName, '') = '' OR t.transactionOperatorName = :transactionOperatorName) " +
            "AND t.transferDate BETWEEN :startTime AND :endTime")
    List<Transfer> findAllByParams(Long accountId, String transactionOperatorName, Timestamp startTime, Timestamp endTime);
}

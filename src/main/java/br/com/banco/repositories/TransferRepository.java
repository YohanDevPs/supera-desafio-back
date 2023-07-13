package br.com.banco.repositories;

import br.com.banco.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Set<Transfer> findByAccountId(Long accountId);

}

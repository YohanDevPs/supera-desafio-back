package br.com.banco.services;

import br.com.banco.entities.Transfer;

import java.util.Set;

public interface TransferService {

    Set<Transfer> findByAccountId(Long id);

}

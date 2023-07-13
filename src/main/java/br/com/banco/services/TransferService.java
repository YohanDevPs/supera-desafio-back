package br.com.banco.services;

import br.com.banco.dtos.TransferDTO;

import java.util.Set;

public interface TransferService {

    Set<TransferDTO> findAllByAccountId(Long id);
}

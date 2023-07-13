package br.com.banco.services;

import br.com.banco.entities.Transfer;
import br.com.banco.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository repository;

    @Override
    public Set<Transfer> findByAccountId(Long id) {
        return repository.findByAccountId(id);
    }
}

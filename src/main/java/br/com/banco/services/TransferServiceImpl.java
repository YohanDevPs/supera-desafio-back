package br.com.banco.services;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.entities.Transfer;
import br.com.banco.mapper.UtilModelMapper;
import br.com.banco.repositories.TransferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static br.com.banco.mapper.UtilModelMapper.parseListObjects;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository repository;

    @Override
    public Set<TransferDTO> findAllByAccountId(Long id) {

        return parseListObjects(repository.findByAccountId(id), TransferDTO.class);
    }



    public Set<Transfer> findAll(Long id) {
        return repository.findByAccountId(id);
    }
}

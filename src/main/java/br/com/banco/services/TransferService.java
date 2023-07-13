package br.com.banco.services;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.dtos.TransferFilterDTO;
import br.com.banco.entities.Transfer;
import br.com.banco.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.banco.utils.mapper.UtilModelMapper.parseListObjects;

@Service
public class TransferService {

    @Autowired
    private TransferRepository repository;


    public List<TransferDTO> findTransfersByFilter(TransferFilterDTO filterDTO) {
        List<Transfer> transfers = repository
                .findAllByParams(filterDTO.getIdAccount(),
                        filterDTO.getTransactionOperatorName(),
                        filterDTO.getStartDate(),
                        filterDTO.getEndDate());

        return parseListObjects(transfers, TransferDTO.class);
    }

    public PagedModel<EntityModel<TransferDTO>> getPagedTransfers(Integer page, Integer limit, TransferFilterDTO filterDTO) {

        int startIndex = page * limit;
        int endIndex = Math.min(startIndex + limit, findTransfersByFilter(filterDTO).size());

        List<TransferDTO> paginatedTransfers;
        if (startIndex <= endIndex) {
            paginatedTransfers = findTransfersByFilter(filterDTO).subList(startIndex, endIndex);
        } else {
            paginatedTransfers = new ArrayList<>();
        }

        List<EntityModel<TransferDTO>> transferModels = paginatedTransfers.stream()
                .map(transfer -> EntityModel.of(transfer))
                .collect(Collectors.toList());

        PagedModel<EntityModel<TransferDTO>> pagedTransfers = PagedModel.of(transferModels,
                new PagedModel.PageMetadata(limit, page, findTransfersByFilter(filterDTO).size()));
        return pagedTransfers;
    }
}

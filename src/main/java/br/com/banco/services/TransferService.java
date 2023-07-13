package br.com.banco.services;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.dtos.TransferFilterDTO;
import br.com.banco.entities.Transfer;
import br.com.banco.exeptions.AccountNotFoundException;
import br.com.banco.repositories.AccountRepository;
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
    @Autowired
    private AccountRepository accountRepository;


    public PagedModel<EntityModel<TransferDTO>> getPagedTransfers(Integer page, Integer limit, TransferFilterDTO filterDTO) {
        validateAccountExists(filterDTO.getIdAccount());

        List<TransferDTO> filteredTransfers = findTransfersByFilter(filterDTO);
        List<TransferDTO> paginatedTransfers = getPaginatedTransfers(filteredTransfers, page, limit);
        List<EntityModel<TransferDTO>> transferModels = mapToEntityModels(paginatedTransfers);

        validatePageLimit(page, filteredTransfers.size(), limit);

        PagedModel.PageMetadata pageMetadata = createPageMetadata(limit, page, filteredTransfers.size());
        return PagedModel.of(transferModels, pageMetadata);
    }

    public List<TransferDTO> findTransfersByFilter(TransferFilterDTO filterDTO) {
        List<Transfer> transfers = repository.findAllByParams(filterDTO.getIdAccount(),
                filterDTO.getTransactionOperatorName(),
                filterDTO.getStartDate(),
                filterDTO.getEndDate());

        return parseListObjects(transfers, TransferDTO.class);
    }

    private void validateAccountExists(Long accountId) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada");
        }
    }

    private void validatePageLimit(Integer page, Integer totalElements, Integer limit) {
        PagedModel.PageMetadata pageMetadata = createPageMetadata(limit, page, totalElements);
        if (pageMetadata.getTotalPages() < page) {
            throw new IllegalArgumentException("Excedeu limite de páginas");
        }
    }

    private List<TransferDTO> getPaginatedTransfers(List<TransferDTO> transfers, Integer page, Integer limit) {
        int startIndex = page * limit;
        int endIndex = Math.min(startIndex + limit, transfers.size());
        return (startIndex <= endIndex) ? transfers.subList(startIndex, endIndex) : new ArrayList<>();
    }

    private List<EntityModel<TransferDTO>> mapToEntityModels(List<TransferDTO> transfers) {
        return transfers.stream().map(EntityModel::of).collect(Collectors.toList());
    }

    private PagedModel.PageMetadata createPageMetadata(Integer limit, Integer page, Integer totalElements) {
        return new PagedModel.PageMetadata(limit, page, totalElements);
    }
}

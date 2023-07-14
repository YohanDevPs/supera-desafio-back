package br.com.banco.services;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.dtos.TransferFilterDTO;
import br.com.banco.exeptions.AccountNotFoundException;
import br.com.banco.exeptions.PageLimitExceededException;
import br.com.banco.repositories.AccountRepository;
import br.com.banco.repositories.TransferRepository;
import br.com.banco.response.CustomPagedTransfersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.banco.utils.hatoes.HateoasUtils.addHateoasLinks;
import static br.com.banco.utils.mapper.UtilModelMapper.parseListObjects;

@Service
public class TransferService {

    @Autowired
    private TransferRepository repository;
    @Autowired
    private AccountRepository accountRepository;

    public CustomPagedTransfersResponse getCustomPagesTransfersResponse(Integer page, Integer limit, TransferFilterDTO filterDTO) {
        validateAccountExists(filterDTO.getIdAccount());

        List<TransferDTO> filteredTransfers = findTransfersByFilter(filterDTO);

        BigDecimal totalBalance = calculateBalance(filteredTransfers);

        List<TransferDTO> paginatedTransfers = getPaginatedTransfers(filteredTransfers, page, limit);
        List<EntityModel<TransferDTO>> transferModels = mapToEntityModels(paginatedTransfers);

        PagedModel.PageMetadata pageMetadata = createPageMetadata(limit, page, filteredTransfers.size());

        validatePageLimit(page, pageMetadata);

        BigDecimal periodBalance = calculateBalance(
                PagedModel.of(transferModels, pageMetadata).getContent().stream()
                        .map(EntityModel::getContent)
                        .collect(Collectors.toList())
        );

        PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(transferModels, pageMetadata);

        addHateoasLinks(pagedModel, page, filterDTO);

        return new CustomPagedTransfersResponse(pagedModel, totalBalance, periodBalance);
    }


    BigDecimal calculateBalance(List<TransferDTO> filteredTransfers) {
        return filteredTransfers.stream()
                .map(TransferDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    List<TransferDTO> findTransfersByFilter(TransferFilterDTO filterDTO) {
        var transfers = repository.findAllByParams(filterDTO.getIdAccount(),
                filterDTO.getTransactionOperatorName(),
                filterDTO.getStartDate(),
                filterDTO.getEndDate());

        return parseListObjects(transfers, TransferDTO.class);
    }
    //OK
    void validateAccountExists(Long accountId) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada");
        }
    }
    //OK
    void validatePageLimit(Integer page, PagedModel.PageMetadata pageMetadata) {
        if (pageMetadata.getTotalPages() < page + 1) {
            throw new PageLimitExceededException("Excedeu limite de páginas");
        }
    }

    //OK
    List<TransferDTO> getPaginatedTransfers(List<TransferDTO> transfers, Integer page, Integer limit) {
        int startIndex = page * limit;
        int endIndex = Math.min(startIndex + limit, transfers.size());
        return (startIndex <= endIndex) ? transfers.subList(startIndex, endIndex) : new ArrayList<>();
    }

    //OK
    List<EntityModel<TransferDTO>> mapToEntityModels(List<TransferDTO> transfers) {
        return transfers.stream().map(EntityModel::of).collect(Collectors.toList());
    }

    //OK
    PagedModel.PageMetadata createPageMetadata(Integer limit, Integer page, Integer totalElements) {
        return new PagedModel.PageMetadata(limit, page, totalElements);
    }
}

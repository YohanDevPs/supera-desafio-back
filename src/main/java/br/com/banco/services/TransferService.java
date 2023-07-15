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

        BigDecimal periodBalance  = calculateBalance(filteredTransfers);

        List<TransferDTO> paginatedTransfers = getPaginatedTransfers(filteredTransfers, page, limit);
        List<EntityModel<TransferDTO>> transferModels = mapToEntityModels(paginatedTransfers);

        PagedModel.PageMetadata pageMetadata = createPageMetadata(limit, page, filteredTransfers.size());

        if(pageMetadata.getTotalElements() == 0) {
            PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(transferModels, pageMetadata);
            return new CustomPagedTransfersResponse(pagedModel, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        validatePageLimit(page, pageMetadata);

        BigDecimal totalBalance = calculateBalance(
                parseListObjects(repository
                        .findAllByAccountId(filterDTO.getIdAccount()), TransferDTO.class)
        );

        PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(transferModels, pageMetadata);

        addHateoasLinks(pagedModel, page, filterDTO);

        return new CustomPagedTransfersResponse(pagedModel, totalBalance , periodBalance);
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

    void validateAccountExists(Long accountId) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new AccountNotFoundException(String.format("Conta de número [%s] não encontrada", accountId));
        }
    }

    void validatePageLimit(Integer page, PagedModel.PageMetadata pageMetadata) {
        if (pageMetadata.getTotalPages() < page) {
            throw new PageLimitExceededException("Página não encontrada");
        }
    }

    List<TransferDTO> getPaginatedTransfers(List<TransferDTO> transfers, Integer page, Integer limit) {
        int startIndex = page * limit;
        int endIndex = Math.min(startIndex + limit, transfers.size());
        return (startIndex <= endIndex) ? transfers.subList(startIndex, endIndex) : new ArrayList<>();
    }

    List<EntityModel<TransferDTO>> mapToEntityModels(List<TransferDTO> transfers) {
        return transfers.stream().map(EntityModel::of).collect(Collectors.toList());
    }

    PagedModel.PageMetadata createPageMetadata(Integer limit, Integer page, Integer totalElements) {
        return new PagedModel.PageMetadata(limit, page, totalElements);
    }
}

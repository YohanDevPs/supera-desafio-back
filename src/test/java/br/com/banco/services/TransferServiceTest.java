package br.com.banco.services;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.dtos.TransferFilterDTO;
import br.com.banco.entities.Account;
import br.com.banco.entities.Transfer;
import br.com.banco.exeptions.AccountNotFoundException;
import br.com.banco.exeptions.PageLimitExceededException;
import br.com.banco.mocks.MockTransfer;
import br.com.banco.repositories.AccountRepository;
import br.com.banco.repositories.TransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransferServiceTest {

    MockTransfer input;
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    void setup() {
        input = new MockTransfer();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateBalance_emptyList() {
        List<TransferDTO> filteredTransfers = new ArrayList<>();

        BigDecimal result = transferService.calculateBalance(filteredTransfers);

        Assertions.assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void testCalculateBalance_nonEmptyList() {
        List<TransferDTO> filteredTransfers = new ArrayList<>();
        filteredTransfers.add(input.createTransferDTO(BigDecimal.valueOf(100)));
        filteredTransfers.add(input.createTransferDTO(BigDecimal.valueOf(50)));
        filteredTransfers.add(input.createTransferDTO(BigDecimal.valueOf(-20)));

        BigDecimal result = transferService.calculateBalance(filteredTransfers);

        Assertions.assertEquals(BigDecimal.valueOf(130), result);
    }

    @Test
    void testFindTransfersByFilter() {
        TransferFilterDTO filterDTO = new TransferFilterDTO();
        filterDTO.setIdAccount(1L);
        filterDTO.setTransactionOperatorName("John Doe");
        filterDTO.setStartDate(new Timestamp(1640995200000L));
        filterDTO.setEndDate(new Timestamp(1672531199000L));

        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer());
        transfers.add(new Transfer());
        when(transferRepository.findAllByParams(filterDTO.getIdAccount(),
                filterDTO.getTransactionOperatorName(),
                filterDTO.getStartDate(),
                filterDTO.getEndDate()))
                .thenReturn(transfers);

        List<TransferDTO> result = transferService.findTransfersByFilter(filterDTO);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testGetPaginatedTransfers() {
        List<TransferDTO> transfers = input.createMockTransfers();
        Integer page = 1;
        Integer limit = 2;

        List<TransferDTO> paginatedTransfers = transferService.getPaginatedTransfers(transfers, page, limit);

        assertEquals(2, paginatedTransfers.size());
        assertEquals(3L, paginatedTransfers.get(0).getId());
        assertEquals(4L, paginatedTransfers.get(1).getId());
    }

    @Test
    public void testMapToEntityModels() {
        List<TransferDTO> transfers = input.createMockTransfers();

        List<EntityModel<TransferDTO>> entityModels = transferService.mapToEntityModels(transfers);

        assertEquals(transfers.size(), entityModels.size());
        for (int i = 0; i < transfers.size(); i++) {
            TransferDTO transferDTO = transfers.get(i);
            EntityModel<TransferDTO> entityModel = entityModels.get(i);

            assertEquals(transferDTO, entityModel.getContent());
        }
    }

    @Test
    public void testCreatePageMetadata() {
        Integer limit = 4;
        Integer page = 2;
        Integer totalElements = 25;

        PagedModel.PageMetadata pageMetadata = transferService.createPageMetadata(limit, page, totalElements);

        assertEquals(Long.valueOf(limit), pageMetadata.getSize());
        assertEquals(Long.valueOf(page), pageMetadata.getNumber());
        assertEquals(Long.valueOf(totalElements), pageMetadata.getTotalElements());
    }

    @Test
    void testValidateAccountExists_existingAccount() {
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account()));

        Assertions.assertDoesNotThrow(() -> transferService.validateAccountExists(accountId));
    }

    @Test
    void testValidateAccountExists_nonExistingAccount() {
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        AccountNotFoundException exception = Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transferService.validateAccountExists(accountId);
        });
        Assertions.assertEquals(String.format("Conta de número [%s] não encontrada", accountId), exception.getMessage());
    }

    @Test
    void testValidatePageLimit_withinLimit() {
        Integer page = 2;
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(10, 2, 30);

        Assertions.assertDoesNotThrow(() -> transferService.validatePageLimit(page, pageMetadata));
    }

    @Test
    void testValidatePageLimit_exceedsLimit() {
        Integer page = 5;
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(10, 2, 30);

        PageLimitExceededException exception = Assertions.assertThrows(PageLimitExceededException.class, () -> {
            transferService.validatePageLimit(page, pageMetadata);
        });
        Assertions.assertEquals("Página não encontrada", exception.getMessage());
    }
}

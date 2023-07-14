package br.com.banco.controllers;

import br.com.banco.dtos.TransferFilterDTO;
import br.com.banco.response.CustomPagedTransfersResponse;
import br.com.banco.services.TransferService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransferControllerTest {

    @InjectMocks
    private TransferController transferController;
    @Mock
    private TransferService transferService;

    @Test
    public void testFindAllByFilters() {
        Long idAccount = 123L;
        Integer page = 0;
        Integer limit = 4;
        String transactionOperatorName = "Fulano";
        Timestamp startDate = new Timestamp(System.currentTimeMillis());
        Timestamp endDate = new Timestamp(System.currentTimeMillis());

        CustomPagedTransfersResponse mockResponse = new CustomPagedTransfersResponse();
        when(transferService.getCustomPagesTransfersResponse(anyInt(), anyInt(), any(TransferFilterDTO.class)))
                .thenReturn(mockResponse);

        CustomPagedTransfersResponse result = transferController.findAllByFilters(idAccount, page, limit,
                transactionOperatorName, startDate, endDate);

        verify(transferService).getCustomPagesTransfersResponse(eq(page), eq(limit), any(TransferFilterDTO.class));
        assertEquals(mockResponse, result);
    }
}
package br.com.banco.response;

import br.com.banco.dtos.TransferDTO;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomPagedTransfersResponseTest {

    @Test
    void testCustomPagedTransfersResponse() {
        List<EntityModel<TransferDTO>> transferModels = new ArrayList<>();
        transferModels.add(EntityModel.of(new TransferDTO()));
        transferModels.add(EntityModel.of(new TransferDTO()));
        PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(transferModels, new PagedModel.PageMetadata(10, 0, 2));
        BigDecimal totalBalance = BigDecimal.valueOf(1000);
        BigDecimal periodBalance = BigDecimal.valueOf(500);

        CustomPagedTransfersResponse response = new CustomPagedTransfersResponse(pagedModel, totalBalance, periodBalance);

        assertNotNull(response);
        assertNotNull(response.getPagedTransfers());
        assertNotNull(response.getTotalBalance());
        assertNotNull(response.getPeriodBalance());
        assertEquals(pagedModel, response.getPagedTransfers());
        assertEquals(totalBalance, response.getTotalBalance());
        assertEquals(periodBalance, response.getPeriodBalance());
    }
}


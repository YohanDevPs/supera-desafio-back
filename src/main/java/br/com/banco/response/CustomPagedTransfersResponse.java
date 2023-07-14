package br.com.banco.response;

import br.com.banco.dtos.TransferDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomPagedTransfersResponse {

    private PagedModel<EntityModel<TransferDTO>> pagedTransfers;
    private BigDecimal totalBalance;
    private BigDecimal periodBalance;
}

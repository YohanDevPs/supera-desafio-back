package br.com.banco.controllers;

import br.com.banco.dtos.TransferFilterDTO;
import br.com.banco.response.CustomPagedTransfersResponse;
import br.com.banco.services.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

import static br.com.banco.utils.time.DateUtils.defineTimesStamps;

@RestController
@RequestMapping("/api/transfer/v1")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="open-api")
public class TransferController {

    @Autowired
    private TransferService service;


    @Operation(summary = "Busca paginação de transferencias e saldos total ou por periodo de tempo", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
            @ApiResponse(responseCode = "404", description = "Pagina não encontrada")
    })
    @GetMapping(value = "/{idAccount}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public CustomPagedTransfersResponse findAllByFilters(
            @PathVariable(value = "idAccount") Long idAccount,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "4") Integer limit,
            @RequestParam(value = "transactionOperatorName", required = false) String transactionOperatorName,
            @RequestParam(value = "startDate", required = false) Timestamp startDate,
            @RequestParam(value = "endDate", required = false) Timestamp endDate
    ) {

        Timestamp[] timestamps = defineTimesStamps(startDate, endDate);
        startDate = timestamps[0];
        endDate = timestamps[1];

        return service.getCustomPagesTransfersResponse(
                page, limit, new TransferFilterDTO(idAccount, transactionOperatorName, startDate, endDate)
        );
    }
}

package br.com.banco.controllers;

import br.com.banco.dtos.TransferFilterDTO;
import br.com.banco.response.CustomPagedTransfersResponse;
import br.com.banco.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

import static br.com.banco.utils.time.DateUtils.defineTimesStamps;

@RestController
@RequestMapping("/api/transfer/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class TransferController {

    @Autowired
    private TransferService service;

    @GetMapping(value = "/{idAccount}")
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

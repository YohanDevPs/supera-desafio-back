package br.com.banco.controllers;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.banco.utils.TimeUtils.getDefaultEndDate;
import static br.com.banco.utils.TimeUtils.getDefaultStartDate;

@RestController
@RequestMapping("/api/transfer/v1")
public class TransferController {

    @Autowired
    private TransferService service;

    @GetMapping(value = "/{idAccount}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PagedModel<EntityModel<TransferDTO>>> findAllById(
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

        List<TransferDTO> transfers = service.findAllByAccountIdAndDates(idAccount, transactionOperatorName, startDate, endDate);

        List<EntityModel<TransferDTO>> transferModels = transfers.stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(transferModels,
                new PagedModel.PageMetadata(limit, page, transferModels.size()));

        return ResponseEntity.ok(pagedModel);
    }

    private Timestamp[] defineTimesStamps(Timestamp startDate, Timestamp endDate) {
        Timestamp[] timestamps = new Timestamp[2];

        if(startDate == null) {
            timestamps[0] = getDefaultStartDate();
        } else {
            timestamps[0] = startDate;
        }

        if (endDate == null) {
            timestamps[1] = getDefaultEndDate();
        } else {
            timestamps[1] = endDate;
        }

        return timestamps;
    }
}

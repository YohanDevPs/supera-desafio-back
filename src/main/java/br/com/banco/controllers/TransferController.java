package br.com.banco.controllers;

import br.com.banco.entities.Transfer;
import br.com.banco.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/transfer/v1")
public class TransferController {

    @Autowired
    private TransferService service;

    @GetMapping(value = "/{idAccount}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Transfer> findById(@PathVariable(value = "idAccount") Long idAccount) throws Exception {
        return service.findByAccountId(idAccount);
    }

}

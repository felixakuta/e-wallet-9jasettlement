package com._ja.e_wallet.controller;

import com._ja.e_wallet.dto.TransactionRequest;
import com._ja.e_wallet.dto.TransferRequest;
import com._ja.e_wallet.model.WalletTransaction;
import com._ja.e_wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/9ja/settlement")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public WalletTransaction create(@RequestBody TransactionRequest req) {
        return service.apply(req);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest req) {
        service.transfer(req);
        return "success";
    }
}
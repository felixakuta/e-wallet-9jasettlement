package com._ja.e_wallet.controller;

import com._ja.e_wallet.dto.CreateWalletRequest;
import com._ja.e_wallet.model.Wallet;
import com._ja.e_wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public Wallet create(@RequestBody CreateWalletRequest req) {
        return walletService.create(req.getInitialBalance());
    }

    @GetMapping("/{id}")
    public Wallet get(@PathVariable Long id) {
        return walletService.get(id);
    }
}
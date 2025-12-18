package com._ja.e_wallet.service;

import com._ja.e_wallet.exception.WalletNotFoundException;
import com._ja.e_wallet.model.Wallet;
import com._ja.e_wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet create(Long initialBalance) {
        return walletRepository.save(
                Wallet.builder().balance(initialBalance).build()
        );
    }

    public Wallet get(Long id) {
        return walletRepository.findById(id)
                .orElseThrow();
    }
}

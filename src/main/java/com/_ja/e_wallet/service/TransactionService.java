package com._ja.e_wallet.service;

import com._ja.e_wallet.dto.TransactionRequest;
import com._ja.e_wallet.dto.TransferRequest;
import com._ja.e_wallet.exception.WalletNotFoundException;
import com._ja.e_wallet.model.Wallet;
import com._ja.e_wallet.model.WalletTransaction;
import com._ja.e_wallet.repository.TransactionRepository;
import com._ja.e_wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public WalletTransaction apply(TransactionRequest req) {

        var existingTx = transactionRepository.findByIdempotencyKey(req.getIdempotencyKey());
        if (existingTx.isPresent()) {
            return existingTx.get();
        }

        Wallet wallet = walletRepository.findById(req.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(req.getWalletId()));

        long newBalance = wallet.getBalance() + req.getAmount();

        if (newBalance < 0) {
            throw new RuntimeException("Insufficient balance.");
        }

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        WalletTransaction tx = WalletTransaction.builder()
                .walletId(req.getWalletId())
                .amount(req.getAmount())
                .idempotencyKey(req.getIdempotencyKey())
                .build();

        return transactionRepository.save(tx);
    }


    @Transactional
    public void transfer(TransferRequest req) {

        // idempotency check
        var existingTx = transactionRepository.findByIdempotencyKey(req.getIdempotencyKey());
        if (existingTx.isPresent()) return;

        Wallet source = walletRepository.findById(req.getSourceWalletId())
                .orElseThrow(() -> new WalletNotFoundException(req.getSourceWalletId()));

        Wallet target = walletRepository.findById(req.getDestinationWalletId())
                .orElseThrow(() -> new WalletNotFoundException(req.getDestinationWalletId()));

        if (source.getBalance() < req.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        source.setBalance(source.getBalance() - req.getAmount());
        target.setBalance(target.getBalance() + req.getAmount());

        walletRepository.save(source);
        walletRepository.save(target);

        transactionRepository.save(
                WalletTransaction.builder()
                        .walletId(req.getSourceWalletId())
                        .amount(-req.getAmount())
                        .idempotencyKey(req.getIdempotencyKey())
                        .build()
        );
    }
}

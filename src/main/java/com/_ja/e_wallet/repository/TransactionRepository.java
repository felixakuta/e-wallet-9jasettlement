package com._ja.e_wallet.repository;

import com._ja.e_wallet.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<WalletTransaction, Long> {
    Optional<WalletTransaction> findByIdempotencyKey(String key);
}
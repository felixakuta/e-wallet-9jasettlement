package com._ja.e_wallet.dto;

import lombok.Data;

/**
 * DTO for Credit/Debit.
 */
    @Data
    public class TransactionRequest {
        private Long walletId;
        private Long amount;
        private String idempotencyKey;
    }
package com._ja.e_wallet.dto;

import lombok.Data;

/**
 * DTO for Transfer.
 */
@Data
public class TransferRequest {
    private Long sourceWalletId;
    private Long destinationWalletId;
    private Long amount;
    private String idempotencyKey;
}
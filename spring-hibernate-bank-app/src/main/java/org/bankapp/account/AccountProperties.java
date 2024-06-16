package org.bankapp.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final double transferFee;

    public AccountProperties(@Value("${account.transfer-commission}") double transferFee) {
        this.transferFee = transferFee;
    }

    public double getTransferFee() {
        return transferFee;
    }
}

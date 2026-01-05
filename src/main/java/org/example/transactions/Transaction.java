package org.example.transactions;

import org.example.Entities.Enums.Result;
import org.example.Entities.Enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Transaction {
    // CLASS ATRIBUTES
    private long operationId;
    private BigDecimal amount;
    private LocalDate date;
    private TransactionType transactionType;
    private Account mainAccount;
    private Account transferToAccount;
    private double creditAmount;
    private Result result;

    // CONSTRUCTORS
    // WITHDRAWAL
    public Transaction(long operationId, BigDecimal amount, LocalDate date, Account mainAccount, Result result) {
        this.operationId = operationId;
        this.amount = amount;
        this.date = date;
        this.transactionType = TransactionType.WITHDRAWAL;
        this.mainAccount = mainAccount;
        this.transferToAccount = null;
        this.creditAmount = 0;
        this.result = result;
    }

    // TRANSFER
    public Transaction(long operationId, BigDecimal amount, LocalDate date, Account mainAccount, Account transferToAccount, Result result) {
        this.operationId = operationId;
        this.amount = amount;
        this.date = date;
        this.transactionType = TransactionType.TRANSFER;
        this.mainAccount = mainAccount;
        this.transferToAccount = transferToAccount;
        this.creditAmount = 0;
        this.result = result;
    }

    // CREDIT
    public Transaction(long operationId, LocalDate date, Account mainAccount, double creditAmount, Result result) {
        this.operationId = operationId;
        this.amount = null;
        this.date = date;
        this.transactionType = TransactionType.CREDIT;
        this.mainAccount = mainAccount;
        this.transferToAccount = null;
        this.creditAmount = creditAmount;
        this.result = result;
    }

    // GETTERS / SETTERS
    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getMainAccount() {
        return mainAccount;
    }

    public void setMainAccount(Account mainAccount) {
        this.mainAccount = mainAccount;
    }

    public Account getTransferToAccount() {
        return transferToAccount;
    }

    public void setTransferToAccount(Account transferToAccount) {
        this.transferToAccount = transferToAccount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

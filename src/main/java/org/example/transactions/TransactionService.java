package org.example.transactions;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransactionService {

    private Map<Long, Transaction> transactionHistory = new HashMap<>();
    private long transactionCounter = 1;

    // El umbral de pco dinero
    private final BigDecimal UMBRAL_ALERTA = new BigDecimal("100");
    private final AccountService accountService;
    private final CreditService creditService;
    private final AlertService alertService;

    public TransactionService(AccountService accountService, CreditService creditService, AlertService alertService) {
        this.accountService = accountService;
        this.creditService = creditService;
        this.alertService = alertService;
    }

    public void withdraw(Long accountId, BigDecimal amount) throws InsufficientFundsException {
        Account account = accountService.getAccountsMap().get(accountId);

        if (account == null) throw new RuntimeException("Account not found");
        // Lógica de saldo y Overdraft
        if (account.getBalance().compareTo(amount) < 0) {
            if (!creditService.applyOverdraft(accountId)) {
                registrarTransaccion(accountId, amount, TransactionType.WITHDRAWAL, Result.FAILED);
                throw new InsufficientFundsException("Insufficient balance.");
            }
        }
        // Restar dinero
        account.setBalance(account.getBalance().subtract(amount));
        registrarTransaccion(accountId, amount, TransactionType.WITHDRAWAL, Result.SUCCESS);
        //si tienenpoco dinero
        comprobarSaldoBajo(account);
    }

    public void transfer(Long srcId, Long destId, BigDecimal amount) throws InvalidTransactionException {
        try {
            // La transferencia usa withdraw, por lo que ya comprueba el saldo ahí
            withdraw(srcId, amount);

            Account destAccount = accountService.getAccountsMap().get(destId);
            destAccount.setBalance(destAccount.getBalance().add(amount));
            registrarTransaccion(destId, amount, TransactionType.TRANSFER, Result.SUCCESS);

        } catch (Exception e) {
            throw new InvalidTransactionException("Error: " + e.getMessage());
        }
    }

    private void comprobarSaldoBajo(Account account) {
        if (account.getBalance().compareTo(UMBRAL_ALERTA) < 0) {
            System.out.println(" WARNING! The account " + account.getAccountId() +
                    " has almost no money: " + account.getBalance() + "€");
            // Llamada al servicio del UML
            alertService.sendLowBalanceAlert(account.getAccountId());
        }
    }


    private void registrarTransaccion(Long accId, BigDecimal amount, TransactionType type, Result result) {
        Transaction tx = new Transaction();
        tx.setOperationId(transactionCounter++);
        tx.setAmount(amount);
        tx.setDate(LocalDateTime.now());
        tx.setType(type);
        tx.setResult(result);
        transactionHistory.put(tx.getOperationId(), tx);
    }
}
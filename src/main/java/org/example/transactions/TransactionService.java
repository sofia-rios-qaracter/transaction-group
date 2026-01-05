package org.example.transactions;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransactionService {
    private Map<Long, Transaction> transactionHistory = new HashMap<>();
    private long transactionCounter = 1;
    private final AccountService accountService;
    private final CreditService creditService;


    public TransactionService(AccountService accountService, CreditService creditService) {
        this.accountService = accountService;
        this.creditService = creditService;
    }

    public void withdraw(Long accountId, BigDecimal amount) throws InsufficientFundsException {
        // Obtenemos la cuenta desde el mapa de AccountService
        Account account = accountService.getAccountsMap().get(accountId);
        if (account == null) {
            throw new RuntimeException("Cuenta no encontrada");
        }

        // Lógica de saldo y Overdraft
        if (account.getBalance().compareTo(amount) < 0) {
            boolean overdraftApproved = creditService.applyOverdraft(accountId);
            if (!overdraftApproved) {
                registrarTransaccion(accountId, amount, TransactionType.WITHDRAWAL, Result.FAILED);
                throw new InsufficientFundsException("Saldo insuficiente y sobregiro denegado.");
            }
        }

        // Actualizar saldo en el mapa
        account.setBalance(account.getBalance().subtract(amount));
        registrarTransaccion(accountId, amount, TransactionType.WITHDRAWAL, Result.SUCCESS);
    }

    public void transfer(Long srcId, Long destId, BigDecimal amount) throws InvalidTransactionException {
        try {
            // Retiro de origen
            withdraw(srcId, amount);
            // Depósito en destino
            Account destAccount = accountService.getAccountsMap().get(destId);
            if (destAccount == null) throw new Exception("Cuenta destino no existe");
                destAccount.setBalance(destAccount.getBalance().add(amount));
                registrarTransaccion(destId, amount, TransactionType.TRANSFER, Result.SUCCESS);

              } catch (Exception e) {
                  throw new InvalidTransactionException("Error en transferencia: " + e.getMessage());
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
        System.out.println("Transacción registrada: " + type + " - ID: " + tx.getOperationId());
    }
}
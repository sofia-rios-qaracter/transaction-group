package org.example.transactions;
import java.math.BigDecimal;

public class ComplianceService {
    private final BigDecimal LIMITE_FRAUDE = new BigDecimal("10000.00");

    public boolean verifyFundsOrigin(Long txId) {
        System.out.println("\n[Compliance] Verificando origen de fondos para Tx ID: " + txId);

        // Simulación simple: ID par = OK, ID impar = Sospechoso
        if (txId % 2 == 0) {
            System.out.println("[Compliance] -> Origen: NOMINA/AHORROS (Verificado).");
            return true;
        } else {
            System.out.println("[Compliance] -> ALERTA: No se puede justificar el origen de los fondos.");
            return false;
        }
    }

    public boolean detectFraud(Transaction tx) {
        System.out.println("\n[Compliance] Analizando fraude para transacción...");
        System.out.println("             Cantidad: " + tx.getAmount());
        System.out.println("             Tipo:  " + tx.getTransactionType());

        // Regla: Si la cantidad es mayor al límite (10,000), devolvemos true (es fraude)
        if (tx.getAmount().compareTo(LIMITE_FRAUDE) > 0) {
            System.out.println("[Compliance] -> Posible fraude detectado.");
            return true;
        }

        System.out.println("[Compliance] -> Transacción limpia. No se detectó fraude.");
        return false;
    }
}
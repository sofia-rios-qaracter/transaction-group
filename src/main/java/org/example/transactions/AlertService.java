package org.example.transactions;

public class AlertService {
    public static void sendLongBalanceAlert(long accountId) {
        System.out.println("Sending long balance alert for account " + accountId + ".");
    }
}

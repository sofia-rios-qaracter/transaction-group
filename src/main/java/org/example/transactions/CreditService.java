package org.example.transactions;

import java.math.BigDecimal;

public class CreditService {
    Transaction t;

    public CreditService(Transaction t){
        this.t = t;
    }

    public boolean applyOverdraft(Long accountId){
        try{
            //
        }catch (Error e){
            return false;
        }
        return true;
    }

    public BigDecimal calculateInterest(Long accountId){
        BigDecimal amount = this.t.getAmount();
        BigDecimal interestRate;

        if((new BigDecimal(100)).compareTo(amount) > 0){
            interestRate = new BigDecimal(0);
        } else if((new BigDecimal(1000)).compareTo(amount) > 0){
            interestRate = new BigDecimal(5);
        } else if((new BigDecimal(10000)).compareTo(amount) > 0){
            interestRate = new BigDecimal(3.5);
        } else{
            interestRate = new BigDecimal(2.5);
        }

        return interestRate;
    }
}

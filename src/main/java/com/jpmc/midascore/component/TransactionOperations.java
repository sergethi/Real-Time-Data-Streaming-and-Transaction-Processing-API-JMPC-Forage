package com.jpmc.midascore.component;
import org.springframework.stereotype.Component;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.TransactionRepository;

@Component
public class TransactionOperations {
    private final TransactionRepository transactionRepository;

    public TransactionOperations(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public void save(TransactionRecord transactionRecord) {
        transactionRepository.save(transactionRecord);
    }

    public boolean validTransaction(long senderId, long recipientId, float senderBalance){
        UserRecord validSenderId ;
        UserRecord validRecipientId ;
        Balance validSenderBalance;
        return validRecipientId && validSenderId && validSenderBalance

    }

}

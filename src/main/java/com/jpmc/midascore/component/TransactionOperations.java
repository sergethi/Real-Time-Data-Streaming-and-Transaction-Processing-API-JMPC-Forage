package com.jpmc.midascore.component;
import java.util.Optional;

import org.springframework.stereotype.Component;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TransactionOperations {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    public TransactionOperations(TransactionRepository transactionRepository, UserRepository userRepository){
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // public void save(TransactionRecord transactionRecord) {
    //     transactionRepository.save(transactionRecord);
    // }

    public boolean validateAndProcess(Transaction transaction){
        Optional<UserRecord> senderOpt = Optional.of(userRepository.findById(transaction.getSenderId())) ;
        Optional<UserRecord> recipientOpt = Optional.of(userRepository.findById(transaction.getRecipientId())) ;
        
        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            UserRecord sender = senderOpt.get();
            UserRecord recipient = recipientOpt.get();

            if(sender.getBalance() >= transaction.getAmount()) {
                //adjust balances
                sender.setBalance(sender.getBalance() - transaction.getAmount());
                recipient.setBalance(recipient.getBalance() + transaction.getAmount());

                //save updated users
                userRepository.save(sender);
                userRepository.save(recipient);

                // Create and save TransactionRecord
                TransactionRecord transactionRecord = new TransactionRecord();
                // transactionRecord.setTransaction(transaction);
                transactionRecord.setSenderId(transaction.getSenderId());
                transactionRecord.setRecipientId(transaction.getRecipientId());
                transactionRecord.setAmount(transaction.getAmount());
                transactionRecord.setSenderBalance(sender.getBalance());
                transactionRecord.setRecipientBalance(recipient.getBalance());

                transactionRepository.save(transactionRecord);

                return true; //transaction processed successfully
            }
            //log out specific sender
            if ("waldorf".equals(sender.getName()) || "waldorf".equals(recipient.getName())){
                logger.info("sender waldorf  balance is: {}", Math.floor(sender.getBalance()));
                logger.info("recipient waldorf  balance is: {}", Math.floor(recipient.getBalance()));
            }
        }
        return false; //transaction validation failed

    }

    public boolean incentiveValidate(Transaction transaction) {
        Optional<UserRecord> senderOpt = Optional.of(userRepository.findById(transaction.getSenderId())) ;
        Optional<UserRecord> recipientOpt = Optional.of(userRepository.findById(transaction.getRecipientId()));

        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            UserRecord sender = senderOpt.get();

            if(sender.getBalance() >= transaction.getAmount()) {
                return true; //transaction processed successfully
            }
        }
        return false; //transaction validation failed
    }

    public void incentiveProcessAndSave(Transaction transaction, float incentiveAmount) {
        Optional<UserRecord> senderOpt = Optional.of(userRepository.findById(transaction.getSenderId())) ;
        Optional<UserRecord> recipientOpt = Optional.of(userRepository.findById(transaction.getRecipientId()));

        UserRecord sender = senderOpt.get();
        UserRecord recipient = recipientOpt.get();

        recipient.setBalance(recipient.getBalance() + incentiveAmount);

        userRepository.save(recipient);

        // Create and save TransactionRecord
        TransactionRecord transactionRecord = new TransactionRecord();
        // transactionRecord.setTransaction(transaction);
        transactionRecord.setSenderId(transaction.getSenderId());
        transactionRecord.setRecipientId(transaction.getRecipientId());
        transactionRecord.setAmount(transaction.getAmount());
        transactionRecord.setSenderBalance(sender.getBalance());
        transactionRecord.setRecipientBalance(recipient.getBalance());

        transactionRepository.save(transactionRecord);

         //log out specific recipient
         if ("wilbur".equals(recipient.getName())){
            logger.info("recipient wilbur's  balance is: {}", Math.floor(recipient.getBalance()));
        }
    }

}

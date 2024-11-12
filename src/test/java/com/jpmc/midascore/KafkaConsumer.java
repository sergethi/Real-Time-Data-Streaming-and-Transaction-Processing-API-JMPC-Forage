package com.jpmc.midascore;

import com.jpmc.midascore.component.TransactionOperations;
import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
@EnableKafka
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private TransactionOperations transactionOperations;

    @Value("${general.kafka-topic}")
    private String topic;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(Transaction transaction) {
        logger.info("Received transaction: {}", transaction);

        // transaction valdation
        if (transactionOperations.validateAndProcess(transaction)){
            logger.info("Transaction processed and saved: {}", transaction);
        } else{
            logger.warn("Transaction validation failed: {}", transaction);
        }
    }

}

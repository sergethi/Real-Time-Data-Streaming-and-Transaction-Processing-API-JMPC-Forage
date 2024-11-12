package com.jpmc.midascore.entity;
import com.jpmc.midascore.foundation.Transaction;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private long senderId;

    @Column(nullable = false)
    private long recipientId;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private float receiverBalance;

    @Column(nullable = false)
    private float senderBalance;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "transcation_id")
    // private Transaction transaction;

      // Getters and Setters
      public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getReceiverBalance() {
        return receiverBalance;
    }

    public void setRecipientBalance(float receiverBalance) {
        this.receiverBalance = receiverBalance;
    }

    public float getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(float senderBalance) {
        this.senderBalance = senderBalance;
    }

    // public Transaction getTransaction() {
    //     return transaction;
    // }

    // public void setTransaction(Transaction transaction) {
    //     this.transaction = transaction;
    // }
}


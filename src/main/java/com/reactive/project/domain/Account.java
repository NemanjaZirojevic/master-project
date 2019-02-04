package com.reactive.project.domain;

import javax.persistence.*;

@Entity
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long accountId;

    @Column(name = "sum")
     public String sum;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    private Account(){};

    public Account(String sum, User user) {
        this.sum = sum;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getSum() {
        return sum;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}

package com.bank.tcmb.dao;

import com.bank.tcmb.entities.TCMBEntity;

import java.math.BigDecimal;

public interface TCMBDao {

    void addTransaction(String from, String to, BigDecimal amount, Boolean error) throws Exception;

    Long saveError(String jsonString);

    TCMBEntity getWithId(Long id);
}

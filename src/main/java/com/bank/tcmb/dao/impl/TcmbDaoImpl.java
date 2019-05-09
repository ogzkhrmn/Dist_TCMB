package com.bank.tcmb.dao.impl;

import com.bank.tcmb.core.annotation.RealTransaction;
import com.bank.tcmb.core.annotation.ServiceBean;
import com.bank.tcmb.dao.AbstractDao;
import com.bank.tcmb.dao.TCMBDao;
import com.bank.tcmb.entities.ErrorEntity;
import com.bank.tcmb.entities.TCMBEntity;
import com.google.gson.Gson;

import java.math.BigDecimal;

@ServiceBean("tCMBDao")
public class TcmbDaoImpl extends AbstractDao implements TCMBDao {

    private Gson gson = new Gson();

    @Override
    @RealTransaction
    public void addTransaction(String from, String to, BigDecimal amount, Boolean error) throws Exception {
        TCMBEntity tcmbEntity = new TCMBEntity();
        tcmbEntity.setFromTckn(from);
        tcmbEntity.setTo_tckn(to);
        tcmbEntity.setAmount(amount);
        tcmbEntity.setAciklama(from + "'dan " + to + " ya " + amount.toString() + " tutarli islem yapilmistir.");
        if (error != null && error) {
            throw new Exception();
        }
        getSessionFactory().save(tcmbEntity);
    }

    @Override
    @RealTransaction
    public Long saveError(String jsonString) {
        ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.setJsonString(jsonString);
        errorEntity.setModule("TCMB_SERVICE");
        errorEntity.setSuccess(false);
        getSessionFactory().save(errorEntity);
        return errorEntity.getId();
    }

    @Override
    @RealTransaction
    public TCMBEntity getWithId(Long id){
        ErrorEntity errorEntity = getSessionFactory().get(ErrorEntity.class, id);
        errorEntity.setSuccess(true);
        getSessionFactory().save(errorEntity);
        TCMBEntity tcmbEntity = null;
        try {
            tcmbEntity = gson.fromJson(errorEntity.getJsonString(), TCMBEntity.class);
            addTransaction(tcmbEntity.getFromTckn(), tcmbEntity.getTo_tckn(), tcmbEntity.getAmount(), false);
        } catch (Exception e) {

        }
        return tcmbEntity;
    }
}

package com.bank.tcmb.service;

import com.bank.tcmb.core.annotation.Bean;
import com.bank.tcmb.dao.TCMBDao;
import com.bank.tcmb.entities.TCMBEntity;
import com.bank.tcmb.model.TCMBErrorRequest;
import com.bank.tcmb.model.TCMBResponse;
import com.bank.tcmb.model.TCMBRequest;
import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@Path("/tcmb")
public class TCMBService implements Serializable {

    private static final Gson gson = new Gson();

    @Bean
    private static TCMBDao tCMBDao;

    @POST
    @Path("/transaction")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TCMBResponse isUserSecure(TCMBRequest request) {
        TCMBResponse response = new TCMBResponse();
        try {
            tCMBDao.addTransaction(request.getFrom(), request.getFrom(), request.getAmount(), request.getError());
            response.setSuccess(true);
        } catch (Exception e) {
            TCMBEntity tcmbEntity = new TCMBEntity();
            tcmbEntity.setAmount(request.getAmount());
            tcmbEntity.setTo_tckn(request.getTo());
            tcmbEntity.setAmount(request.getAmount());
            tcmbEntity.setFromTckn(request.getFrom());
            tcmbEntity.setAciklama(request.getFrom() + "'dan " + request.getTo() + " ya " + request.getAmount().toString() + " tutarli islem yapilmistir.");
            response.setId(tCMBDao.saveError(gson.toJson(tcmbEntity)));
            response.setSuccess(false);
        }
        return response;
    }

    @POST
    @Path("/control")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean isAlive() {
        return true;
    }


    @POST
    @Path("/tryAgain")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TCMBResponse callError(TCMBErrorRequest request) {
        TCMBResponse response = new TCMBResponse();
        TCMBEntity tcmbEntity = tCMBDao.getWithId(request.getId());
        response.setSuccess(true);
        if (tcmbEntity == null) {
            response.setId(tCMBDao.saveError(gson.toJson(tcmbEntity)));
            response.setSuccess(false);
        }
        return response;
    }
}

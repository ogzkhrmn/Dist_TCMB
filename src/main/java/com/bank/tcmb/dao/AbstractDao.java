package com.bank.tcmb.dao;

import com.bank.tcmb.core.HibernateConfiguration;
import org.hibernate.Session;

public class AbstractDao {

    protected Session getSessionFactory(){
        return HibernateConfiguration.getSession().getCurrentSession();
    }

}

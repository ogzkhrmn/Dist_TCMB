package com.bank.tcmb.core.handler;

import com.bank.tcmb.core.HibernateConfiguration;
import com.bank.tcmb.core.annotation.RealTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicInvocationHandler implements InvocationHandler {

    private Object handler;

    public DynamicInvocationHandler(Object handler) {
        this.handler = handler;
    }

    @Transactional
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Session session = HibernateConfiguration.getSession().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (method.getAnnotationsByType(RealTransaction.class) != null) {
                Object o = method.invoke(handler, args);
                transaction.commit();
                return o;
            }
        } catch (Exception e) {
            transaction.rollback();
            throw new Exception(e);
        }
        return proxy;
    }
}

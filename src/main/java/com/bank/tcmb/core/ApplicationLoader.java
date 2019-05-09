package com.bank.tcmb.core;

import com.bank.tcmb.core.annotation.Bean;
import com.bank.tcmb.core.annotation.ServiceBean;
import com.bank.tcmb.core.handler.DynamicInvocationHandler;
import com.bank.tcmb.core.model.AnnotatedFieldModel;
import com.bank.tcmb.core.util.BeanUtil;
import com.bank.tcmb.core.util.PackageScanner;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.List;

class ApplicationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateConfiguration.class);

    static void loadApp() {
        try {
            List<Class> classList = PackageScanner.getClasses(ApplicationProperties.getProperty("app.package"));
            classList.forEach(item -> {
                Annotation[] t = item.getAnnotationsByType(ServiceBean.class);
                if (ArrayUtils.isNotEmpty(t)) {
                    ServiceBean bean = (ServiceBean) t[0];
                    if (StringUtils.isEmpty(bean.value())) {
                        BeanUtil.addBean(item.getSimpleName(), item);
                    } else {
                        BeanUtil.addBean(bean.value(), item);
                    }
                }
                for (Field field : item.getDeclaredFields()) {
                    Annotation[] a = field.getAnnotationsByType(Bean.class);
                    if (ArrayUtils.isNotEmpty(a)) {
                        BeanUtil.addField(field.getName(), new AnnotatedFieldModel(item, field));
                    }
                }
                item.getFields();
            });
        } catch (Exception e) {
            LOGGER.error("Package Reading problem ", e);
        }
    }

    static void addProxy() {
        try {
            BeanUtil.getFields().keySet().forEach(item -> {
                try {
                    AnnotatedFieldModel annotatedFieldModel = BeanUtil.getField(item);
                    Class clazz = BeanUtil.getBean(item);
                    Object object = Proxy.newProxyInstance(clazz.getClassLoader(),
                            new Class[]{annotatedFieldModel.getField().getType()},
                            new DynamicInvocationHandler(Class.forName(clazz.getName()).newInstance()));
                    annotatedFieldModel.getField().setAccessible(true);
                    annotatedFieldModel.getField().set(annotatedFieldModel.getClazz().cast(Class.forName(annotatedFieldModel.getClazz().getName()).newInstance()), object);
                } catch (Exception e) {
                    LOGGER.error("Bean creation error", e);
                }
            });
        } catch (Exception e) {
            LOGGER.error("Package Reading problem ", e);
        }
    }

}

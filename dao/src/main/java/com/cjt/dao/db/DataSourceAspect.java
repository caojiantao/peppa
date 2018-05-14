package com.cjt.dao.db;

import com.cjt.common.util.ExceptionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author caojiantao
 */
@Aspect
@Component
public class DataSourceAspect {

    private final Logger logger = Logger.getLogger(DataSourceAspect.class);

    private final static String EXECUTION = "execution(* com.cjt.dao..*.*(..)) && !execution(* com.cjt.dao.db..*.*(..))";

    private final DynamicDataSource dataSource;

    @Autowired
    public DataSourceAspect(DynamicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Before(EXECUTION)
    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class<?>[] clazz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = clazz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSourceAnnotation.class)) {
                DataSourceAnnotation data = m.getAnnotation(DataSourceAnnotation.class);
                dataSource.setDataSourceKey(data.target().getKey());
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }

    @After(EXECUTION)
    public void after() {
        dataSource.clearDataSourceKey();
    }
}

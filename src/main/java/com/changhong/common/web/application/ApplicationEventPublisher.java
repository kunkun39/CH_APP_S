package com.changhong.common.web.application;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:16
 */
@Service("applicationEventPublisher")
public class ApplicationEventPublisher implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext ctx;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(ctx);
    }

    public static void publish(ApplicationEvent event) {
        if (isRunningInTomcat()) {
            ctx.publishEvent(event);
        }
    }

    private static boolean isRunningInTomcat() {
        return ctx != null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setCtx(applicationContext);
    }

    public static ApplicationContext getCtx() {
        return ctx;
    }

    private void setCtx(ApplicationContext ctx) {
        ApplicationEventPublisher.ctx = ctx;
    }
}

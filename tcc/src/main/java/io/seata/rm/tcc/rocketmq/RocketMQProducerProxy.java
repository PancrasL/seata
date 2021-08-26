package io.seata.rm.tcc.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.seata.core.context.RootContext;

public class RocketMQProducerProxy implements InvocationHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(RocketMQScanner.class);

    private final DefaultMQProducer target;

    public RocketMQProducerProxy(DefaultMQProducer target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 不在全局事务中，调用代理对象的原方法
        if(!RootContext.inGlobalTransaction()){
            return method.invoke(target);
        }

        // 在全局事务中，做一些额外的操作
        // do something
        LOGGER.info("invoke method: [{}]", method.getName());
        return method.invoke(target);
    }

    public static Object getProxyInstance(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new RocketMQProducerProxy((DefaultMQProducer) obj));
    }
}

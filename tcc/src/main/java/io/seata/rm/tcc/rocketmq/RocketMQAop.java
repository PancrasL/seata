package io.seata.rm.tcc.rocketmq;

import org.apache.rocketmq.client.producer.SendResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import io.seata.core.context.RootContext;

@Aspect
public class RocketMQAop {
    TCCRocketMQ tccRocketMQ = new TCCRocketMQImpl();

    @Around("execution(* org.apache.rocketmq.client.producer.DefaultMQProducer.send(org.apache.rocketmq.common.message.Message))")
    // TODO：将RocketMQ纳入到全局事务中
    public SendResult send(ProceedingJoinPoint point) throws Throwable {
        if (RootContext.inGlobalTransaction()) {
            System.out.printf("In Global Transaction");
            Object returnValue = point.proceed(point.getArgs());
            return (SendResult) returnValue;
        } else {
            System.out.printf("Not In Global Transaction");
            return (SendResult) point.proceed(point.getArgs());
        }
    }
}

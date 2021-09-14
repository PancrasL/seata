package io.seata.rm.tcc.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface TCCRocketMQ {

    @TwoPhaseBusinessAction(name = "TccRocketMQ", commitMethod = "commit", rollbackMethod = "rollback")
    boolean prepare(BusinessActionContext actionContext, DefaultMQProducer defaultMQProducer, Message msg);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}

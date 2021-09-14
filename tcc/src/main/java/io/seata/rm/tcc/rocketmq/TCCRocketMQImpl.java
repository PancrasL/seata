package io.seata.rm.tcc.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.seata.rm.tcc.api.BusinessActionContext;

public class TCCRocketMQImpl implements TCCRocketMQ {
    private static final Logger LOGGER = LoggerFactory.getLogger(TCCRocketMQImpl.class);

    private DefaultMQProducer defaultMQProducer;
    private Message msg = null;
    private SendResult sendResult = null;

    @Override
    public boolean prepare(BusinessActionContext actionContext, DefaultMQProducer defaultMQProducer, Message msg) {
        this.defaultMQProducer = defaultMQProducer;
        this.msg = msg;
        this.sendResult = MQUtils.halfSend(defaultMQProducer, msg);

        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        MQUtils.confirm(defaultMQProducer, msg, sendResult);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        MQUtils.cancel(defaultMQProducer, msg, sendResult);
        return true;
    }
}

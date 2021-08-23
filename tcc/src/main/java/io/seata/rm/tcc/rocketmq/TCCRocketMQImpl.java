package io.seata.rm.tcc.rocketmq;

import io.seata.rm.tcc.api.BusinessActionContext;

public class TCCRocketMQImpl implements TCCRocketMQ {
    @Override
    public boolean prepare(BusinessActionContext actionContext, int a) {
        String xid = actionContext.getXid();
        if (!xid.isEmpty()) {
            System.out.println("TccActionOne prepare, xid:" + xid);
        }
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        if (!xid.isEmpty()) {
            System.out.println("TccActionOne prepare, xid:" + xid);
        }
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        if (!xid.isEmpty()) {
            System.out.println("TccActionOne prepare, xid:" + xid);
        }
        return true;
    }
}

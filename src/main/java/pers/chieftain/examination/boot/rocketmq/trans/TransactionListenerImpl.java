package pers.chieftain.examination.boot.rocketmq.trans;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 实际处理业务的类，可能是本地带事务性的方法中处理
 * @author asus
 */
public class TransactionListenerImpl implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        System.out.println("====executeLocalTransaction=======");
        
        String body = new String(msg.getBody());
        String key = msg.getKeys();
        String transactionId = msg.getTransactionId();
        System.out.println("transactionId="+transactionId+", key="+key+", body="+body);
        // 执行本地事务begin TODO
        
        // 执行本地事务end TODO

        int status = Integer.parseInt(arg.toString());

        //二次确认消息，然后消费者可以消费
        if(status == 1){
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        //回滚消息，broker端会删除半消息
        if(status == 2){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        //broker端会进行回查消息，再或者什么都不响应
        if(status == 3){
            return LocalTransactionState.UNKNOW;
        }

        return null;
    }
    
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {

        System.out.println("====checkLocalTransaction=======");
        String body = new String(msg.getBody());
        String key = msg.getKeys();
        String transactionId = msg.getTransactionId();
        System.out.println("transactionId="+transactionId+", key="+key+", body="+body);
        //要么commit 要么rollback
        //可以根据key去检查本地事务消息是否完成
        return LocalTransactionState.COMMIT_MESSAGE;
    }
    
}
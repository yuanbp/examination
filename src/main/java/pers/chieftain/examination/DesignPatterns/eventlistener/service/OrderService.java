package pers.chieftain.examination.DesignPatterns.eventlistener.service;

import pers.chieftain.examination.DesignPatterns.eventlistener.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author chieftain
 * @date 2020/4/2 15:30
 */
@Service
public class OrderService {

    @Autowired
    private ApplicationContext context;

    /**
     * 模拟保存订单发送短信通知微信通知
     */
    public void saveOrder() {
        System.out.println("订单保存成功");
        // 发布一个事件
        context.publishEvent(new OrderEvent("订单创建成功啦"));
    }
}

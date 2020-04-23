package pers.chieftain.examination.DesignPatterns.eventlistener.listener;

import pers.chieftain.examination.DesignPatterns.eventlistener.event.OrderEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author chieftain
 * @date 2020/4/2 15:35
 */
@Component
public class SmsListener implements ApplicationListener<OrderEvent> {

    @Override
    public void onApplicationEvent(@NotNull OrderEvent orderEvent) {
        System.out.println("监听到订单事件,开始执行发送短信" + orderEvent.getSource());
    }
}

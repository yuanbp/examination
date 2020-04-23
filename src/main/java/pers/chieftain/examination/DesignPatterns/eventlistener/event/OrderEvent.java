package pers.chieftain.examination.DesignPatterns.eventlistener.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author chieftain
 * @date 2020/4/2 15:31
 */
public class OrderEvent extends ApplicationEvent {

    public OrderEvent(Object source) {
        super(source);
    }
}

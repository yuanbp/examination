package pers.chieftain.examination.DesignPatterns.eventlistener.controller;

import pers.chieftain.examination.DesignPatterns.eventlistener.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chieftain
 * @date 2020/4/2 15:38
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public void save() {
        orderService.saveOrder();
    }
}

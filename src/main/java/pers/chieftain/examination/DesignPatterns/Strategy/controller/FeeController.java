package pers.chieftain.examination.DesignPatterns.Strategy.controller;

import pers.chieftain.examination.DesignPatterns.Strategy.service.CalcFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chieftain
 * @date 2020/4/2 15:58
 */
@RestController
@RequestMapping("/fee")
public class FeeController {

    @Autowired
    private CalcFeeService calcFeeService;

    @GetMapping("/calcfee")
    public void calcFee() {
        calcFeeService.totalFee("other", 100);
        calcFeeService.totalFee("normal", 100);
        calcFeeService.totalFee("vip", 100);
    }
}

package com.chieftain.examination;

import java.math.BigDecimal;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-04-03
 * @time 19:32
 */
public class TestBo {

    public TestBo() {

    }

    public TestBo(String id, BigDecimal money) {
        this.id = id;
        this.money = money;
    }

    private String id;
    private BigDecimal money;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}

package com.study.load_balance.轮询;

public class Weight {

    // 固定的权重值
    private Integer weight ;

    // 动态的权重值：当前权重值
    private Integer currentWeight;

    private String ip;

    public Weight(Integer weight, Integer currentWeight, String ip) {
        this.weight = weight;
        this.currentWeight = currentWeight;
        this.ip = ip;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

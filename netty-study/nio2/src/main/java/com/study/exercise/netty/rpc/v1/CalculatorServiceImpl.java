package com.study.exercise.netty.rpc.v1;

public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public double add(double op1, double op2) {
        return op1 + op2;
    }

    @Override
    public double subtraction(double op1, double op2) {
        return op1 - op2;
    }

    @Override
    public double multiply(double op1, double op2) {
        return op1 * op2;
    }

    public static void main(String[] args) {
        // 本地直接调用
        CalculatorService service = new CalculatorServiceImpl();
        System.out.println(service.add(1.0, 2.0));
    }

}

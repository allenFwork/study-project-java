package com.study.reactors.manyReactor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            TCPReactor reactor = new TCPReactor(1333);
//                new Thread(reactor).start();
            reactor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
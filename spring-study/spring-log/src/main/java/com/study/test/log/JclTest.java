package com.study.test.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JclTest {

    public static void main(String[] args) {
        Log log = LogFactory.getLog("jcl");
        log.info("jcl log ... ");
    }

}

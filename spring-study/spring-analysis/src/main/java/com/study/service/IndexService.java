package com.study.service;

import java.util.List;

public interface IndexService {

    List query();

    List query(String id);

    void update();

}

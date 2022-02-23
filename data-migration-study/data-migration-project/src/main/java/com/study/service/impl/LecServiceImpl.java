package com.study.service.impl;

import com.study.mapper.lec.interfaces.LenovoSkujcsjSpkcBackupMapper;
import com.study.mapper.lec2.interfaces.LenovoSkujcsjSpkcJavaMapper;
import com.study.service.LecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecServiceImpl implements LecService {

    @Autowired
    private LenovoSkujcsjSpkcJavaMapper lenovoSkujcsjSpkcJavaMapper;

    @Autowired
    private LenovoSkujcsjSpkcBackupMapper lenovoSkujcsjSpkcBackupMapper;

    @Override
    public int selectAll() {
        int count1 = lenovoSkujcsjSpkcBackupMapper.countAll();
        int count2 = lenovoSkujcsjSpkcJavaMapper.countAll();
        System.out.println("count1=" + count1 + ", count2=" + count2);
        return 0;
    }

}

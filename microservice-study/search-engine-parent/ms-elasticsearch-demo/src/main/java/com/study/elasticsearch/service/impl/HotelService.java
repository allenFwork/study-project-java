package com.study.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.elasticsearch.mapper.HotelMapper;
import com.study.elasticsearch.pojo.Hotel;
import com.study.elasticsearch.service.IHotelService;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

}

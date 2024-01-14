package com.study.mybatis_plus.service.impl;

import com.study.mybatis_plus.domain.po.Address;
import com.study.mybatis_plus.mapper.AddressMapper;
import com.study.mybatis_plus.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allen
 * @since 2024-01-14
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}

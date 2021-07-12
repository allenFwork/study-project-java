package cn.com.epicc.ms.cpi.mapper.picc_cpi;

import cn.com.epicc.ms.cpi.entity.picc_cpi.AlipayPolicy;
import cn.com.epicc.ms.cpi.entity.picc_cpi.AlipayPolicyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlipayPolicyMapper {
    long countByExample(AlipayPolicyExample example);

    int deleteByExample(AlipayPolicyExample example);

    int insert(AlipayPolicy record);

    int insertSelective(AlipayPolicy record);

    List<AlipayPolicy> selectByExample(AlipayPolicyExample example);

    int updateByExampleSelective(@Param("record") AlipayPolicy record, @Param("example") AlipayPolicyExample example);

    int updateByExample(@Param("record") AlipayPolicy record, @Param("example") AlipayPolicyExample example);
}
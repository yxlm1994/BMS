package com.cloud.dao;

import com.cloud.pojo.Ad_Promotion;

public interface Ad_PromotionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Ad_Promotion record);

    int insertSelective(Ad_Promotion record);

    Ad_Promotion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Ad_Promotion record);

    int updateByPrimaryKey(Ad_Promotion record);
}
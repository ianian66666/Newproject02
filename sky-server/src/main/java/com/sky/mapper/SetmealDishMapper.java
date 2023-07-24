package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根據菜品id查是否有對應的套餐id
     * @param ids
     * @return
     */

    List<Long> getSetmealByDishIds(List<Long> ids);
}

package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper  {

    /**
     * 批量插入
     * @param flavorList
     */
//    @AutoFill(OperationType.INSERT)
    void insertBatch(List<DishFlavor> flavorList);

    /**
     * 根據菜品id刪除對應的口味數據
     * @param dishId
     */
    @Delete("delete  from  sky_take_out.dish_flavor where  dish_id =#{dishId}")
    void deleteByDishId(Long dishId);
}

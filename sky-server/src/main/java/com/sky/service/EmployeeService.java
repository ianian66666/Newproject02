package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增員工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分頁查詢員工
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageEmpQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 啟用禁用員工帳號
     * @param id
     * @param status
     */
    void stopOrStart(Long id, Integer status);

    /**
     * 根據id查詢員工訊息
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 修改員工數據
     * @param employeeDTO
     */
    void updateEmp(EmployeeDTO employeeDTO);
}

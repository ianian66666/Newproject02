package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    @PostMapping
    @ApiOperation("新增員工")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增員工:{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 員工分頁查詢
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("員工分頁查詢")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("員工分頁查詢：{}",employeePageQueryDTO);
        PageResult pageResult =  employeeService.pageEmpQuery(employeePageQueryDTO);
        return  Result.success(pageResult);
    }

    /**
     * 啟用停用帳號
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("啟用停用帳號")
    public Result stopOrStart(Long id,@PathVariable Integer status ){
        log.info("啟用停用帳號：{}",id ,status);
        employeeService.stopOrStart(id,status);
        return  Result.success();
    }

    /**
     * 根據id查詢員工訊息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根據id查詢員工訊息")
    public  Result<Employee> getById(@PathVariable Long id){
            Employee employee = employeeService.getById(id);

            return  Result.success(employee);

    }

    /**
     * 修改員工數據
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改員工數據")
    public  Result  updateEmp(@RequestBody EmployeeDTO employeeDTO){
        log.info("修改員工數據：{}",employeeDTO);
            employeeService.updateEmp(employeeDTO);
            return Result.success();
    }

}

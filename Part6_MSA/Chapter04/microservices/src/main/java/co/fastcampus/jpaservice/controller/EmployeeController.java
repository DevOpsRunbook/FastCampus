package co.fastcampus.jpaservice.controller;

import co.fastcampus.jpaservice.dto.EmployeeDTO;
import co.fastcampus.jpaservice.exception.DataNotFoundException;
import co.fastcampus.jpaservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    public EmployeeDTO create(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.create(employeeDTO);
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value ="EMPLOYEE")
    public EmployeeDTO getById(@PathVariable("id") Long id) throws DataNotFoundException {
        return employeeService.getById(id);
    }

    @GetMapping("/")
    public List<EmployeeDTO> getAll(){
        return employeeService.getAll();
    }

    @PutMapping("/{id}")
    @CacheEvict(key = "#id", value = "EMPLOYEE")
    public EmployeeDTO update(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO){
        return employeeService.update(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id", value = "EMPLOYEE")
    public void delete(@PathVariable("id") Long id){
        employeeService.delete(id);
    }

}

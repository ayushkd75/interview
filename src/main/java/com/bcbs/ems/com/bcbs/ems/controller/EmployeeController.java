package com.bcbs.ems.controller;

import com.bcbs.ems.exceptions.EmployeeAlreadyExistsException;
import com.bcbs.ems.exceptions.EmployeeNotFoundException;
import com.bcbs.ems.exceptions.GenericApiException;
import com.bcbs.ems.model.Employee;
import com.bcbs.ems.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/ems")
@Api(produces = "application/json", value = "Operations pertaining to employees in the application")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    
    
    @PostMapping("/employees")
    @ApiOperation(value = "Create a new employee", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a new employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try{
            Employee savedEmployee = employeeRepository.save(employee);
            return new ResponseEntity<Employee>(savedEmployee, HttpStatus.OK);
        }
        catch(Exception e){
            if( e instanceof ConstraintViolationException){
                throw new EmployeeAlreadyExistsException(e.getMessage());
            }
            throw new GenericApiException(e.getMessage());
        }
    }
    
    

    @GetMapping("/employees")
    @ApiOperation(value = "View all employees", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all employees"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    
    
    @GetMapping("employees/{id}")
    @ApiOperation(value = "Retrieve specific employee details with the supplied employee id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the employee details with the supplied id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) {
        Optional employee = employeeRepository.findById(id);
        if(!employee.isPresent()){
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>((Employee) employee.get(), HttpStatus.OK);
    }

    
    
    @PutMapping("/employees/{id}")
    @ApiOperation(value = "Update a specific employee information", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated employee information"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<Employee> UpdateEmployee(@RequestBody Employee employee) {
        try{
            Optional findEmployee = employeeRepository.findById(employee.getEmployeeId());
            if(!findEmployee.isPresent()){
                throw new EmployeeNotFoundException("Employee with id "+employee.getEmployeeId()+" is not present");
            }
            Employee savedEmployee = employeeRepository.save(employee);
            return new ResponseEntity<Employee>(savedEmployee, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    

    @DeleteMapping("/employees/{id}")
    @ApiOperation(value = "Deletes specific employee with the supplied employee id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deletes the specific employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public void delete(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
    }

}
package com.bcbs.ems.service;

import com.bcbs.ems.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeService extends CrudRepository<Employee, Integer> {

}

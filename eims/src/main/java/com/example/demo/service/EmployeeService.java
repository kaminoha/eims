package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public List<Employee> retriveAllEmployee () {
        return employeeRepository.findAll();
    }

    public long countByGender(String gender) {
        return employeeRepository.countByGender(gender);
    }

    public List<Employee> topFiveByOrderByFinance() {
        return employeeRepository.findTop5ByOrderByFinance_RevenueDesc();
    }
}

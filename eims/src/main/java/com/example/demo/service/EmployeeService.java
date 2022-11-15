package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.dto.RatioResponse;
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

    public RatioResponse countByGender() {

        RatioResponse response = new RatioResponse();

        long male = employeeRepository.countByGender("Male");
        long female = employeeRepository.countByGender("Female");

        response.setFemale(female);
        response.setMale(male);

        return response;
    }

    public List<Employee> topFiveByOrderByFinance() {
        return employeeRepository.findTop5ByOrderByFinance_RevenueDesc();
    }
}

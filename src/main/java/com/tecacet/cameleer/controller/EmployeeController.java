package com.tecacet.cameleer.controller;

import com.tecacet.cameleer.model.Employee;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Produce(value = "direct:start")
    private ProducerTemplate template;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String createEmployee(@RequestParam int id, @RequestParam String name, @RequestParam String designation) {

        Employee emp = new Employee();
        emp.setName(name);
        emp.setDesignation(designation);

        template.sendBody(template.getDefaultEndpoint(), emp);
        return "OK";
    }

}

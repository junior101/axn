package com.axn.rest.controller;

import com.axn.database.entity.Admin;
import com.axn.database.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public final class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(value = "/list/{page}/{size}", method = RequestMethod.GET)
    public final ResponseEntity list(@PathVariable("page") int page, @PathVariable("size") int size) {
        List<Admin> admins = adminService.findAll(page, size);
        if (admins == null || admins.size() == 0) return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }
}

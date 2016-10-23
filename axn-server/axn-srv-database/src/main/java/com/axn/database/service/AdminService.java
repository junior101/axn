package com.axn.database.service;

import com.axn.database.entity.Admin;
import com.axn.database.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Return list of admins
     * If page == size, return all records
     *
     * @param page page of table
     * @param size count of records in page
     * @return List<Admin>
     */
    public final List<Admin> findAll(final int page, final int size) {
        List<Admin> admins;
        if (page == 0 && size == 0 || page < 0 || size < 0) admins = adminRepository.findAll();
        else admins = adminRepository.findAll(new PageRequest(page, size)).getContent();
        return admins;
    }
}

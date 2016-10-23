package com.axn.database.test.service;

import com.axn.database.test.config.ConfigTestDataSpring;
import com.axn.database.entity.Admin;
import com.axn.database.service.AdminService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SuppressWarnings("CanBeFinal")
@ContextConfiguration(classes = ConfigTestDataSpring.class)
@RunWith(SpringJUnit4ClassRunner.class)
public final class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Test
    public final void findAll_NotNull() {
        List<Admin> admins = adminService.findAll(0,0);
        Assert.assertNotNull(admins);
    }

    @Test
    public final void findAll_isValidCount() {
        List<Admin> admins = adminService.findAll(1,2);
        Assert.assertEquals(admins.size(), 2);
    }

    @Test
    public final void findAll_isValid() {
        List<Admin> admins = adminService.findAll(1,2);
        Assert.assertNotNull(admins);
        Assert.assertNotNull(admins.get(0));
        Assert.assertNotNull(admins.get(1));
    }
}

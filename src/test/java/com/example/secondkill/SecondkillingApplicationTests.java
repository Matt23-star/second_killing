package com.example.secondkill;

import com.example.secondkill.entity.Sponsor;
import com.example.secondkill.mapper.SponsorMapper;
import com.example.secondkill.service.ISponsorService;
import com.example.secondkill.utils.MyBatisPlusUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecondkillingApplicationTests {

    @Autowired
    ISponsorService sponsorService;

    @Autowired
    SponsorMapper sponsorMapper;


    @Test
    void contextLoads() {
        MyBatisPlusUtils.Generator();
    }

    @Test
    void testMybatis(){
        Sponsor sponsor = new Sponsor();
        sponsor.setId("114");
        sponsor.setName("name");
        sponsor.setDescription("description");
        sponsorService.addSponsor(sponsor);
//        List<Sponsor> sponsors = new ArrayList<>();
////        QueryWrapper<Sponsor> sponsorWrapper = new QueryWrapper<>();
////        sponsorWrapper.select();
//
//        System.out.println(sponsorMapper.selectList(null));
    }
}

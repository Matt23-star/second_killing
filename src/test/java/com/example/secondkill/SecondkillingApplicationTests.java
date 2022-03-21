package com.example.secondkill;

import com.example.secondkill.entity.pojo.Sponsor;
import com.example.secondkill.entity.pojo.UserKillState;
import com.example.secondkill.mapper.SponsorMapper;
import com.example.secondkill.service.ISponsorService;
import com.example.secondkill.utils.MyBatisPlusUtils;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class SecondkillingApplicationTests {

    @Autowired
    ISponsorService sponsorService;

    @Autowired
    SponsorMapper sponsorMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void test() {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> resultLists = new ArrayList<List<Integer>>();
        List<Integer> resultList;
        int length = nums.length;
        for (int i = 0; i < length - 2; i++) {
            int left = i + 1;
            int right = length - 1;
            int target = -nums[i];
            while (left < right) {
                if (nums[left] + nums[right] < target) {
                    left++;
                } else if (nums[left] + nums[right] > target) {
                    right--;
                } else {
                    resultList = new ArrayList<>();
                    resultList.add(nums[i]);
                    resultList.add(nums[left]);
                    resultList.add(nums[right]);
                    resultLists.add(resultList);
                }
            }
        }
        return resultLists;
    }

    @Test
    void contextLoads() {
        MyBatisPlusUtils.Generator();
    }

    @Test
    void test1(){
        System.out.println(new Date());
    }
    @Test
    void testMybatis() {
        Sponsor sponsor = new Sponsor();
        sponsor.setId("115");
        sponsor.setName("name");
        sponsor.setDescription("description");
        sponsorService.addSponsor(sponsor);
//        List<Sponsor> sponsors = new ArrayList<>();
////        QueryWrapper<Sponsor> sponsorWrapper = new QueryWrapper<>();
////        sponsorWrapper.select();
//
//        System.out.println(sponsorMapper.selectList(null));
    }

    @Test
    void redisTest() {
        redisTemplate.opsForValue().get("111");
//        UserKillState userKillState = new UserKillState();
//        userKillState.setState("正常");
//        userKillState.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
//        userKillState.setUserId("2");
//        userKillState.setKillActivityId("3");
//        userKillState.setTime(new Date(System.currentTimeMillis()));
//        redisTemplate.opsForList().leftPush("1" + ".userStateList", userKillState);
//        UserKillState userKillState2 = (UserKillState) redisTemplate.opsForList().leftPop("1" + ".userStateList");
//        System.out.println(userKillState2.toString());
        //        redisTemplate.opsForValue().set("a", "a");
//        if(1)
//        if(redisTemplate.opsForValue().decrement()<0)
//        {
//            redisTemplate.opsForValue().increment();
//        }
//
//        System.out.println(redisTemplate.opsForValue().get("a"));
    }
}


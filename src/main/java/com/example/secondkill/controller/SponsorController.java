package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.KillImformationDTO;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.ProductInformation;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.service.IKill_informationService;
import com.example.secondkill.service.IProduct_informationService;
import com.example.secondkill.service.ISponsorService;
import com.example.secondkill.service.IUserService;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/sponsor")
@Api(tags = "发起人接口")
public class SponsorController {

    @Autowired
    private IKill_informationService kill_informationService;

    @Autowired
    private ISponsorService sponsorService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProduct_informationService productInformationService;

    // 秒杀活动添加接口
    @PostMapping("/addSecondKill")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "productId", value = "产品id", dataType = "String", paramType = "query", required = true),
//            @ApiImplicitParam(name = "sponsorId", value = "发起人id", dataType = "String", paramType = "query", required = true),
//            @ApiImplicitParam(name = "productNum", value = "产品数量", paramType = "query", required = true),
//            @ApiImplicitParam(name = "description", value = "秒杀活动描述", dataType = "String", paramType = "query", required = true),
//            @ApiImplicitParam(name = "buyMaximum", value = "购买最大量", paramType = "query", required = true),
//            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Date", paramType = "query", required = true),
//            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Date", paramType = "query", required = true)
//    })
    public Result addSecondKill(@RequestBody KillImformationDTO killImformationDTO){
        return kill_informationService.addSecondKill(killImformationDTO);
    }

    // 根据指定条件获取秒杀活动List
    @GetMapping("/universalKillSelect/{colName}/{value}/{orderBy}/{aOrD}/{from}/{num}")
    public Result<List<KillInformation>> universalKillSelect(@PathVariable("colName") String colName,
                                                  @PathVariable("value") String value,
                                                  @PathVariable("orderBy") String orderBy,
                                                  @PathVariable("aOrD") String aOrD,
                                                  @PathVariable("from") Integer from,
                                                  @PathVariable("num") Integer num){
        List<KillInformation> killInformations
                = sponsorService.universalKillSelect(colName, value, orderBy, aOrD, from, num);
        return ResultUtils.success(killInformations);
    }

    // 秒杀活动详情接口
    @GetMapping("/getKillDetails/{killId}")
    public Result<KillInformation> getKillDetails(@PathVariable String killId) {
        KillInformation killInformation = sponsorService.getKillDetails(killId);
        if (killInformation == null) return ResultUtils.error
                (new ResultMessage(410, "Second Kill Not Exists"));
        else return ResultUtils.success(killInformation);
    }

    // 秒杀活动修改
    @PostMapping("/updateKillInfo")
    public Result updateKillInfo (@RequestBody KillInformation killInformation) {
        return sponsorService.updateKillInfo(killInformation);
    }

    // 秒杀活动删除
    @PostMapping("/deleteKillInfo/{killId}")
    public Result deleteKillInfo (@PathVariable("killId") String killId) {
        return sponsorService.deleteKillInfo(killId);
    }

    @GetMapping("/getProductsInfo/{colName}/{value}/{orderBy}/{aOrD}/{from}/{num}")
    public Result getProducts(@PathVariable("colName") String colName,
                              @PathVariable("value") String value,
                              @PathVariable("orderBy") String orderBy,
                              @PathVariable("aOrD") String aOrD,
                              @PathVariable("from") Integer from,
                              @PathVariable("num") Integer num){
        return productInformationService.selectProductList(colName, value, orderBy, aOrD, from, num);
    }

    @PostMapping("/deleteProduct/{productId}")
    public Result deleteProduct(@PathVariable("productId") String productId){
        return productInformationService.deleteProduct(productId);
    }

    @PostMapping("/addProduct")
    public Result addProduct(@RequestBody ProductInformation productInformation) {
        return productInformationService.addProduct(productInformation);
    }

    @PostMapping("/updateProduct")
    public Result updateProduct(@RequestBody ProductInformation productInformation){
        return productInformationService.updateProduct(productInformation);
    }

    @PostMapping("/login")
    public Result adminLogin(@RequestParam String name,
                             @RequestParam String password,
                             HttpServletResponse response){
        return sponsorService.login(name,password,response);
    }

    @GetMapping("/getUserList/{colName}/{value}/{orderBy}/{aOrD}/{from}/{num}")
    public Result queryUserList(@PathVariable("colName") String colName,
                                @PathVariable("value") String value,
                                @PathVariable("orderBy") String orderBy,
                                @PathVariable("aOrD") String aOrD,
                                @PathVariable("from") Integer from,
                                @PathVariable("num") Integer num){
        return userService.selectUserList(colName, value, orderBy, aOrD, from, num);
    }

    @PostMapping("/deleteUser/{userId}")
    public Result deleteUser(@PathVariable("userId") String userId){
        return userService.deleteUser(userId);
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

}


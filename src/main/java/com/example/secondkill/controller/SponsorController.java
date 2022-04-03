package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.KillImformationDTO;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.service.IKill_informationService;
import com.example.secondkill.service.ISponsorService;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class SponsorController {

    @Autowired
    private IKill_informationService kill_informationService;

    @Autowired
    private ISponsorService sponsorService;

    // 秒杀活动添加接口
    @PostMapping("/addSecondKill")
    public Result addSecondKill(@RequestBody KillImformationDTO killImformationDTO){
        return kill_informationService.addSecondKill(killImformationDTO);
    }

    // 根据指定条件获取秒杀活动List
    @GetMapping("universalKillSelect/{colName}/{value}/{orderBy}/{aOrD}/{from}/{num}")
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
    @GetMapping("getKillDetails/{killId}")
    public Result<KillInformation> getKillDetails(@PathVariable String killId) {
        KillInformation killInformation = sponsorService.getKillDetails(killId);
        if (killInformation == null) return ResultUtils.error
                (new ResultMessage(410, "Second Kill Not Exists"));
        else return ResultUtils.success(killInformation);
    }

    // 秒杀活动修改
    @PostMapping("updateKillInfo")
    public Result updateKillInfo (@RequestBody KillInformation killInformation) {
        return sponsorService.updateKillInfo(killInformation);
    }

    // 秒杀活动删除
    @PostMapping("deleteKillInfo/{killId}")
    public Result deleteKillInfo (@PathVariable("killId") String killId) {
        return sponsorService.deleteKillInfo(killId);
    }

    //管理员登录
    @PostMapping("/login")
    public Result adminLogin(@RequestParam("name")String name,
                             @RequestParam("password")String password,
                             HttpServletResponse response){
        return sponsorService.login(name,password,response);
    }
}


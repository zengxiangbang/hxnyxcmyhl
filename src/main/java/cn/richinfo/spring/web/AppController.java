package cn.richinfo.spring.web;

import cn.richinfo.common.annotation.VerifyLogin;
import cn.richinfo.common.enumeration.Role;
import cn.richinfo.spring.domain.ActiveUser;
import cn.richinfo.spring.domain.GuessRecord;
import cn.richinfo.spring.domain.UserPrize;
import cn.richinfo.spring.exception.ActiveIllegalStateException;
import cn.richinfo.spring.result.Result;
import cn.richinfo.spring.result.ResultCode;
import cn.richinfo.spring.service.GuessRecordService;
import cn.richinfo.spring.service.UserPrizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author Grant
 */
@Controller
@RequestMapping("/app")
public class AppController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private UserPrizeService userPrizeService;

    @Autowired
    private GuessRecordService guessRecordService;


    /**
     * 用户奖品列表
     * @return  List<UserPrize> 用户奖品记录
     */
    @RequestMapping(value = "/prize.do", method = RequestMethod.GET)
    @ResponseBody
    @VerifyLogin(role = Role.mobileuser)
    public Result<List<UserPrize>> getUserPrize(){
        ActiveUser activeUser = getUser();
        try{
            UserPrize userPrize = new UserPrize();
            userPrize.setUserId(activeUser.getUserId());
            List<UserPrize> userPrizeList = userPrizeService.queryAll(userPrize);
            logger.info("userPrize.do | mobile : " + activeUser.getAccount() + " | 查询中奖记录成功");
            return new Result<List<UserPrize>>(ResultCode.SUCCESS.getCode(), userPrizeList);
        }catch (Exception e){
            logger.error("userPrize.do | mobile : " + activeUser.getAccount() + " | 查询中奖记录异常", e);
            return Result.createByError();
        }
    }


    /**
     * 获取用户当日的猜谜记录
     * @return Result<GuessRecord>
     */
    @RequestMapping(value = "/guess.do", method = RequestMethod.GET)
    @ResponseBody
    @VerifyLogin(role = Role.mobileuser)
    public Result<GuessRecord> getGuessRecord(){
        ActiveUser activeUser = getUser();
        try {
            GuessRecord guessRecord = guessRecordService.getTodayGuessRecord(activeUser.getUserId());
            logger.info("guess.do | mobile : " + activeUser.getAccount() + " | 查询猜谜记录成功");
            return Result.createBySuccess(guessRecord);
        }catch (Exception e){
            logger.error("guess.do | mobile : " + activeUser.getAccount() + " | 查询猜谜记录异常", e);
            return Result.createByError();
        }

    }


    /**
     * 获取用户当日的猜谜记录
     * @return Result
     */
    @RequestMapping(value = "/help.do", method = RequestMethod.GET)
    @ResponseBody
    @VerifyLogin(role = Role.mobileuser)
    public Result help(){
        ActiveUser activeUser = getUser();
        try {
            byte time = guessRecordService.updateHelpTime(activeUser.getUserId());
            logger.info("help.do | mobile : " + activeUser.getAccount() + " | 第" + time + "次求助成功");
            return Result.createBySuccess();
        }catch (Exception e){
            logger.error("help.do | mobile : " + activeUser.getAccount() + " | 更新求助次数异常", e);
            return Result.createByError();
        }
    }


    /**
     * 用户抽奖
     * @return Result
     */
    @RequestMapping(value = "/lottery.do", method = RequestMethod.GET)
    @ResponseBody
    @VerifyLogin(role = Role.mobileuser)
    public Result lottery(){
        ActiveUser activeUser = getUser();
        try {
            UserPrize userPrize = userPrizeService.lottery(activeUser.getUserId());
            if(userPrize == null){
                logger.info("lottery.do | mobile : " + activeUser.getAccount() + " | 用户抽奖失败, 奖品库存不足");
            }else{
                logger.info("lottery.do | mobile : " + activeUser.getAccount() + " | 用户抽奖成功 " + userPrize.getPrizeName());
            }
            return Result.createBySuccess(userPrize);

        }catch (ActiveIllegalStateException e){
            logger.error("lottery.do | mobile : " + activeUser.getAccount() + " | 用户抽奖异常");
            return Result.createByError();
        }catch (Exception e){
            logger.error("lottery.do | mobile : " + activeUser.getAccount() + " | 用户抽奖异常", e);
            return Result.createByError();
        }
    }

    /**
     * 更新用户答题状态
     * @return Result
     */
    @RequestMapping(value = "/submit.do", method = RequestMethod.GET)
    @ResponseBody
    @VerifyLogin(role = Role.mobileuser)
    public Result updateGuessRecord(){
        ActiveUser activeUser = getUser();
        try {
            GuessRecord todayGuessRecord = guessRecordService.getTodayGuessRecord(activeUser.getUserId());
            todayGuessRecord.setIsSuccess((byte) 1);
            todayGuessRecord.setUpdateTime(new Date());
            guessRecordService.update(todayGuessRecord);
            logger.info("submit.do | mobile : " + activeUser.getAccount() + " | 更新用户答题状态成功");
            return Result.createBySuccess();
        }catch (Exception e){
            logger.error("submit.do | mobile : " + activeUser.getAccount() + " | 更新用户答题状态异常", e);
            return Result.createByError();
        }
    }











}

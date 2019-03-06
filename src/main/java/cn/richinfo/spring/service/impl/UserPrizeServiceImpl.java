package cn.richinfo.spring.service.impl;

import cn.richinfo.spring.domain.GuessRecord;
import cn.richinfo.spring.domain.Prize;
import cn.richinfo.spring.domain.UserPrize;
import cn.richinfo.spring.exception.ActiveIllegalStateException;
import cn.richinfo.spring.mapper.PrizeMapper;
import cn.richinfo.spring.mapper.UserPrizeMapper;
import cn.richinfo.spring.service.ActiveUserService;
import cn.richinfo.spring.service.GuessRecordService;
import cn.richinfo.spring.service.UserPrizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 用户中奖记录(UserPrize)表服务实现类
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
@Service("userPrizeService")
@Transactional(rollbackOn = Exception.class)
public class UserPrizeServiceImpl implements UserPrizeService {

    private static final Logger logger = LoggerFactory.getLogger(UserPrizeServiceImpl.class);

    @Resource
    private UserPrizeMapper userPrizeMapper;
    @Resource
    private GuessRecordService guessRecordService;
    @Resource
    private PrizeMapper prizeMapper;
    @Resource
    private ActiveUserService activeUserService;

    /**
     * 通过ID查询单条数据
     *
     * @param  userPrizeId 主键
     * @return 实例对象
     */
    @Override
    public UserPrize queryById(Long  userPrizeId) {
        return this.userPrizeMapper.queryById(userPrizeId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserPrize> queryAllByLimit(int offset, int limit) {
        return this.userPrizeMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userPrize 实例对象
     * @return 实例对象
     */
    @Override
    public UserPrize insert(UserPrize userPrize) {
        this.userPrizeMapper.insert(userPrize);
        return userPrize;
    }

    /**
     * 修改数据
     *
     * @param userPrize 实例对象
     * @return 实例对象
     */
    @Override
    public UserPrize update(UserPrize userPrize) {
        this.userPrizeMapper.update(userPrize);
        return this.queryById(userPrize.getUserPrizeId());
    }

    /**
     * 通过主键删除数据
     *
     * @param  userPrizeId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userPrizeId ) {
        return this.userPrizeMapper.deleteById(userPrizeId) > 0;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userPrize 实例对象
     * @return 对象列表
     */
    @Override
    public List<UserPrize> queryAll(UserPrize userPrize) {
        return userPrizeMapper.queryAll(userPrize);
    }

    /**
     * 用户抽奖
     *
     * @param userId 用户id
     * @return 更新行数
     */
    @Override
    public UserPrize lottery(Long userId) {
        GuessRecord todayGuessRecord = guessRecordService.getTodayGuessRecord(userId);
        if(todayGuessRecord.getIsSuccess() != 1){
            logger.warn(" userId : " + userId + " | 用户未猜谜");
            throw new ActiveIllegalStateException("User is not guess");
        }
        if(todayGuessRecord.getIsLottery() == 1){
            logger.warn(" userId : " + userId + " | 用户已抽奖");
            throw new ActiveIllegalStateException("User has already drawn");
        }

        UserPrize userPrize = null;
        List<Prize> prizeList = prizeMapper.queryAll(new Prize());
        Random random = new Random();
        int index = random.nextInt(1000000) + 1;
        logger.info(" userId : " + userId + " | 抽奖生成的随机数: " + index);
        Integer prizeListIndex = 0;
        //抽奖
        for (int j = 0; j < prizeList.size(); j++) {
            Prize prize = prizeList.get(j);
            if(index <= prize.getProbabilityInterval()){
                prizeListIndex = j;
                break;
            }
        }

        while (prizeListIndex >= 0){
            //奖品剩余数量
            int row = prizeMapper.updateSurplusQuantity(prizeList.get(prizeListIndex).getPrizeId());
            if(row > 0){
                userPrize = new UserPrize();
                break;
            }else{
                prizeListIndex--;
            }
        }


        //已中奖
        if(userPrize != null){
            //添加用户奖品记录
            userPrize.setPrizeId(prizeList.get(prizeListIndex).getPrizeId());
            userPrize.setPrizeName(prizeList.get(prizeListIndex).getPrizeName());
            userPrize.setUserId(userId);
            userPrize.setCreateTime(new Date());
            userPrizeMapper.insert(userPrize);
        }

        //如果中了一元话费,需添加三佳优惠券
        if(prizeList.get(prizeListIndex).getPrizeId() == 2){
            logger.warn(" userId : " + userId + " | 添加三佳优惠券");
            UserPrize sanJiaUserPrize = new UserPrize();
            sanJiaUserPrize.setPrizeId(prizeList.get(0).getPrizeId());
            sanJiaUserPrize.setPrizeName(prizeList.get(0).getPrizeName());
            sanJiaUserPrize.setUserId(userId);
            sanJiaUserPrize.setCreateTime(new Date());
            userPrizeMapper.insert(sanJiaUserPrize);
        }
        //更新猜谜记录
        todayGuessRecord.setIsLottery((byte) 1);
        todayGuessRecord.setUpdateTime(new Date());
        guessRecordService.update(todayGuessRecord);
        return userPrize;
    }



}


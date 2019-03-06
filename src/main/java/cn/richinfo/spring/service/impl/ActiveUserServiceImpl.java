package cn.richinfo.spring.service.impl;

import cn.richinfo.common.utils.DES;
import cn.richinfo.common.utils.RSADecryptUtil;
import cn.richinfo.spring.domain.ActiveUser;
import cn.richinfo.spring.exception.InactiveUserException;
import cn.richinfo.spring.mapper.ActiveUserMapper;
import cn.richinfo.spring.service.ActiveUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 活动用户(ActiveUser)表服务实现类
 *
 * @author Grant
 * @since 2019-02-01 15:41:20
 */
@Service("activeUserService")
@Transactional
public class ActiveUserServiceImpl implements ActiveUserService {

    private static final Logger logger = LoggerFactory.getLogger(ActiveUserServiceImpl.class);
    @Resource
    private ActiveUserMapper activeUserMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public ActiveUser queryById(Long userId) {
        return this.activeUserMapper.queryById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ActiveUser> queryAllByLimit(int offset, int limit) {
        return this.activeUserMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param activeUser 实例对象
     * @return 实例对象
     */
    @Override
    public ActiveUser insert(ActiveUser activeUser) {
        this.activeUserMapper.insert(activeUser);
        return activeUser;
    }

    /**
     * 修改数据
     *
     * @param activeUser 实例对象
     * @return 实例对象
     */
    @Override
    public ActiveUser update(ActiveUser activeUser) {
        this.activeUserMapper.update(activeUser);
        return this.queryById(activeUser.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.activeUserMapper.deleteById(userId) > 0;
    }

    /**
     * 用户登录
     * @param mobile
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public ActiveUser login(String mobile, Integer type) throws Exception {
        String phone = "";
        String phoneStr;
        if(type == 2){
            phoneStr = RSADecryptUtil.decrypt(mobile);
        }else{
            phoneStr = DES.decryptDES(mobile,"10086DES");
        }
        int index = phoneStr.indexOf("=");
        if(index > 0){
            phone = phoneStr.substring(index + 1);
        }
        boolean isTianJinMobile = isTianJinMobile(phone);
        if(!isTianJinMobile){
            logger.warn(" mobile : " + phone + " | 非天津号码");
            throw new InactiveUserException("非天津号码");
        }
        ActiveUser activeUser = new ActiveUser();
        activeUser.setAccount(phone);
        List<ActiveUser> activeUserList = activeUserMapper.queryAll(activeUser);
        if(activeUserList == null || activeUserList.size() == 0){
            Random random = new Random();
            byte questionType = (byte) random.nextInt(3);
            activeUser.setQuestionType(questionType);
            activeUser.setCreateTime(new Date());
            activeUserMapper.insert(activeUser);
            return activeUser;
        }
        return activeUserList.get(0);
    }

    /**
     * 是否为天津号码
     * @param account
     * @return boolean
     */
    @Override
    public boolean isTianJinMobile(String account) {
        if(StringUtils.isNotBlank(account)){
            String substringMobile = account.substring(0, 7);
            int count = activeUserMapper.getTianJinMobile(substringMobile);
            if(count > 0){
                return true;
            }
        }
        return false;
    }


}
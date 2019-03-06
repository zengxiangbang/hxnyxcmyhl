package cn.richinfo.spring.service;

import cn.richinfo.spring.domain.ActiveUser;
import java.util.List;

/**
 * 活动用户(ActiveUser)表服务接口
 *
 * @author Grant
 * @since 2019-02-01 15:41:20
 */
public interface ActiveUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    ActiveUser queryById(Long userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ActiveUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param activeUser 实例对象
     * @return 实例对象
     */
    ActiveUser insert(ActiveUser activeUser);

    /**
     * 修改数据
     *
     * @param activeUser 实例对象
     * @return 实例对象
     */
    ActiveUser update(ActiveUser activeUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

    /**
     * 用户登录
     * @param mobile
     * @param type
     * @return
     * @throws Exception
     */
    ActiveUser login(String mobile, Integer type) throws Exception;

    /**
     * 是否为天津号码
     * @param account
     * @return boolean
     */
    boolean isTianJinMobile(String account);
}
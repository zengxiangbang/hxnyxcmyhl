package cn.richinfo.spring.service;

import cn.richinfo.spring.domain.UserPrize;
import java.util.List;

/**
 * 用户中奖记录(UserPrize)表服务接口
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public interface UserPrizeService {

    /**
     * 通过ID查询单条数据
     *
     * @param  userPrizeId 主键
     * @return 实例对象
     */
    UserPrize queryById(Long userPrizeId );


    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserPrize> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userPrize 实例对象
     * @return 实例对象
     */
    UserPrize insert(UserPrize userPrize);

    /**
     * 修改数据
     *
     * @param userPrize 实例对象
     * @return 实例对象
     */
    UserPrize update(UserPrize userPrize);

    /**
     * 通过主键删除数据
     *
     * @param  userPrizeId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userPrizeId );

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userPrize 实例对象
     * @return 对象列表
     */
    List<UserPrize> queryAll(UserPrize userPrize);

    /**
     * 用户抽奖
     *
     * @param userId 用户id
     * @return 更新行数
     */
    UserPrize lottery(Long userId);
    
}
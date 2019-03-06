package cn.richinfo.spring.mapper;

import cn.richinfo.spring.domain.UserPrize;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户中奖记录(UserPrize)表数据库访问层
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public interface UserPrizeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param  userPrizeId
     * @return 实例对象
     */
    UserPrize queryById(Long userPrizeId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserPrize> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userPrize 实例对象
     * @return 对象列表
     */
    List<UserPrize> queryAll(UserPrize userPrize);

    /**
     * 新增数据
     *
     * @param userPrize 实例对象
     * @return 影响行数
     */
    int insert(UserPrize userPrize);

    /**
     * 修改数据
     *
     * @param userPrize 实例对象
     * @return 影响行数
     */
    int update(UserPrize userPrize);

    /**
     * 通过主键删除数据
     *
     * @param  userPrizeId 主键
     * @return 影响行数
     */
    int deleteById(Long userPrizeId );

    /**
     * 根据userId查询多条数据
     *
     * @param userId 查询起始位置
     * @return 对象列表
     */
    List<UserPrize> queryAllByUserId(Long userId);
}
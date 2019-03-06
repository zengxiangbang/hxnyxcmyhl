package cn.richinfo.spring.mapper;

import cn.richinfo.spring.domain.ActiveUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 活动用户(ActiveUser)表数据库访问层
 *
 * @author Grant
 * @since 2019-02-01 15:41:20
 */
public interface ActiveUserMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    ActiveUser queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ActiveUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param activeUser 实例对象
     * @return 对象列表
     */
    List<ActiveUser> queryAll(ActiveUser activeUser);

    /**
     * 新增数据
     *
     * @param activeUser 实例对象
     * @return 影响行数
     */
    int insert(ActiveUser activeUser);

    /**
     * 修改数据
     *
     * @param activeUser 实例对象
     * @return 影响行数
     */
    int update(ActiveUser activeUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

    /**
     * 查询天津号码段
     * @param mobile
     * @return List<String>
     */
    int getTianJinMobile(@Param("mobile") String mobile);

}
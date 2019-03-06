package cn.richinfo.spring.mapper;

import cn.richinfo.spring.domain.Prize;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 活动奖品(Prize)表数据库访问层
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public interface PrizeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param prizeId 主键
     * @return 实例对象
     */
    Prize queryById(Integer prizeId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Prize> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param prize 实例对象
     * @return 对象列表
     */
    List<Prize> queryAll(Prize prize);

    /**
     * 新增数据
     *
     * @param prize 实例对象
     * @return 影响行数
     */
    int insert(Prize prize);

    /**
     * 修改数据
     *
     * @param prize 实例对象
     * @return 影响行数
     */
    int update(Prize prize);

    /**
     * 通过主键删除数据
     *
     * @param prizeId 主键
     * @return 影响行数
     */
    int deleteById(Integer prizeId);
    /**
     * 修改奖品剩余数量
     *
     * @param prizeId 奖品id
     * @return 影响行数
     */
    int updateSurplusQuantity(Integer prizeId);
}
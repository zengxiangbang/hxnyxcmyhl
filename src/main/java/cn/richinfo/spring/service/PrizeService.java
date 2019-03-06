package cn.richinfo.spring.service;

import cn.richinfo.spring.domain.Prize;
import java.util.List;

/**
 * 活动奖品(Prize)表服务接口
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public interface PrizeService {

    /**
     * 通过ID查询单条数据
     *
     * @param prizeId 主键
     * @return 实例对象
     */
    Prize queryById(Integer prizeId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Prize> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param prize 实例对象
     * @return 实例对象
     */
    Prize insert(Prize prize);

    /**
     * 修改数据
     *
     * @param prize 实例对象
     * @return 实例对象
     */
    Prize update(Prize prize);

    /**
     * 通过主键删除数据
     *
     * @param prizeId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer prizeId);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param prize 实例对象
     * @return 对象列表
     */
    List<Prize> queryAll(Prize prize);

}
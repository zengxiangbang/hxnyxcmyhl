package cn.richinfo.spring.service.impl;

import cn.richinfo.spring.domain.Prize;
import cn.richinfo.spring.mapper.PrizeMapper;
import cn.richinfo.spring.service.PrizeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * 活动奖品(Prize)表服务实现类
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
@Service("prizeService")
@Transactional
public class PrizeServiceImpl implements PrizeService {
    @Resource
    private PrizeMapper prizeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param prizeId 主键
     * @return 实例对象
     */
    @Override
    public Prize queryById(Integer prizeId) {
        return this.prizeMapper.queryById(prizeId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Prize> queryAllByLimit(int offset, int limit) {
        return this.prizeMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param prize 实例对象
     * @return 实例对象
     */
    @Override
    public Prize insert(Prize prize) {
        this.prizeMapper.insert(prize);
        return prize;
    }

    /**
     * 修改数据
     *
     * @param prize 实例对象
     * @return 实例对象
     */
    @Override
    public Prize update(Prize prize) {
        this.prizeMapper.update(prize);
        return this.queryById(prize.getPrizeId());
    }

    /**
     * 通过主键删除数据
     *
     * @param prizeId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer prizeId) {
        return this.prizeMapper.deleteById(prizeId) > 0;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param prize 实例对象
     * @return 对象列表
     */
    @Override
    public List<Prize> queryAll(Prize prize) {
        return prizeMapper.queryAll(prize);
    }
}
package cn.richinfo.spring.service;

import cn.richinfo.spring.domain.GuessRecord;

import java.util.List;

/**
 * 猜谜记录(GuessRecord)表服务接口
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public interface GuessRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    GuessRecord queryById(Long recordId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GuessRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param guessRecord 实例对象
     * @return 实例对象
     */
    GuessRecord insert(GuessRecord guessRecord);

    /**
     * 修改数据
     *
     * @param guessRecord 实例对象
     * @return 实例对象
     */
    GuessRecord update(GuessRecord guessRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 是否成功
     */
    boolean deleteById(Long recordId);

    /**
     * 通过userId查询今天的猜谜记录
     *
     * @param userId 主键
     * @return 实例对象
     */
    GuessRecord getTodayGuessRecord(Long userId);
    /**
     * 更新用户当日的求助次数
     *
     * @param userId 主键
     * @return 求助次数
     */
    byte updateHelpTime(Long userId);
}
package cn.richinfo.spring.mapper;

import cn.richinfo.spring.domain.GuessRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 猜谜记录(GuessRecord)表数据库访问层
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public interface GuessRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    GuessRecord queryById(Long recordId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GuessRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param guessRecord 实例对象
     * @return 对象列表
     */
    List<GuessRecord> queryAll(GuessRecord guessRecord);

    /**
     * 新增数据
     *
     * @param guessRecord 实例对象
     * @return 影响行数
     */
    int insert(GuessRecord guessRecord);

    /**
     * 修改数据
     *
     * @param guessRecord 实例对象
     * @return 影响行数
     */
    int update(GuessRecord guessRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 影响行数
     */
    int deleteById(Long recordId);

    /**
     * 根据userId查询猜谜记录
     * @param userId
     * @return GuessRecord
     */
    GuessRecord getTodayGuessRecord(Long userId);
}
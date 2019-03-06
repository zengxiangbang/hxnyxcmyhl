package cn.richinfo.spring.service.impl;

import cn.richinfo.spring.domain.GuessRecord;
import cn.richinfo.spring.mapper.GuessRecordMapper;
import cn.richinfo.spring.service.GuessRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 猜谜记录(GuessRecord)表服务实现类
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
@Service("guessRecordService")
@Transactional
public class GuessRecordServiceImpl implements GuessRecordService {
    @Resource
    private GuessRecordMapper guessRecordMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    @Override
    public GuessRecord queryById(Long recordId) {
        return this.guessRecordMapper.queryById(recordId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<GuessRecord> queryAllByLimit(int offset, int limit) {
        return this.guessRecordMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param guessRecord 实例对象
     * @return 实例对象
     */
    @Override
    public GuessRecord insert(GuessRecord guessRecord) {
        this.guessRecordMapper.insert(guessRecord);
        return guessRecord;
    }

    /**
     * 修改数据
     *
     * @param guessRecord 实例对象
     * @return 实例对象
     */
    @Override
    public GuessRecord update(GuessRecord guessRecord) {
        this.guessRecordMapper.update(guessRecord);
        return this.queryById(guessRecord.getRecordId());
    }

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long recordId) {
        return this.guessRecordMapper.deleteById(recordId) > 0;
    }

    @Override
    public GuessRecord getTodayGuessRecord(Long userId) {
        GuessRecord guessRecord = guessRecordMapper.getTodayGuessRecord(userId);
        if(guessRecord == null ){
            guessRecord = new GuessRecord();
            guessRecord.setUserId(userId);
            guessRecord.setCreateTime(new Date());
            guessRecord.setHelpTime((byte) 0);
            guessRecord.setIsLottery((byte) 0 );
            guessRecord.setIsSuccess((byte) 0);
            guessRecordMapper.insert(guessRecord);
        }
        return guessRecord;
    }

    @Override
    public byte updateHelpTime(Long userId) {
        GuessRecord todayGuessRecord = getTodayGuessRecord(userId);
        byte helpTime = (byte) (todayGuessRecord.getHelpTime() + 1);
        todayGuessRecord.setHelpTime(helpTime);
        todayGuessRecord.setUpdateTime(new Date());
        guessRecordMapper.update(todayGuessRecord);
        return helpTime;
    }
}
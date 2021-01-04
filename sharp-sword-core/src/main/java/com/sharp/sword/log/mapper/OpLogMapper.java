package com.sharp.sword.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sharp.sword.log.op.OpLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lizheng
 * @date: 12:22 2020/02/16
 * @Description: OpLogMapper
 */
public interface OpLogMapper extends BaseMapper<OpLog> {
    /**
     * 批量插入日志
     * @param logs
     */
    void batchInsert(@Param("logs") List<OpLog> logs);
}

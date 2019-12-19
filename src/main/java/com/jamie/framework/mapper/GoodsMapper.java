package com.jamie.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jamie.framework.bean.Goods;

import java.util.List;

/**
 * @author lizheng
 * @date: 11:12 2019/10/09
 * @Description: GoodsMapper
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> find(Goods goods);
}

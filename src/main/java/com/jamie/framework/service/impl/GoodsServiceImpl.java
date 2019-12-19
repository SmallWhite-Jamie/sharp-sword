package com.jamie.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jamie.framework.bean.Goods;
import com.jamie.framework.mapper.GoodsMapper;
import com.jamie.framework.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author lizheng
 * @date: 11:21 2019/10/09
 * @Description: GoodsServiceImpl
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Goods getGoodsById(String id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public IPage<Goods> list(Page<Goods> page, Goods goods) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>(goods);
//        wrapper.eq("name", goods.getName());
//        wrapper.eq(goods.getMs() != null, "ms", goods.getMs());
        wrapper.orderByDesc("name");
        return goodsMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Goods> find(Goods goods) {
        return goodsMapper.find(goods);
    }
}

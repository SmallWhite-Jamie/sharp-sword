package com.sharp.sword.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sharp.sword.bean.Goods;
import com.sharp.sword.mapper.GoodsMapper;
import com.sharp.sword.service.GoodsService;
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

package com.sharp.sword.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sharp.sword.bean.Goods;

import java.util.List;

/**
 * @author lizheng
 * @date: 11:20 2019/10/09
 * @Description: GoodsService
 */
public interface GoodsService {
    Goods getGoodsById(String id);

    IPage<Goods> list(Page<Goods> page, Goods goods);

    List<Goods> find(Goods goods);
}

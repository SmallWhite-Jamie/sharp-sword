package com.sharp.sword.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sharp.sword.bean.Goods;
import com.sharp.sword.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lizheng
 * @date: 11:13 2019/10/09
 * @Description: GoodsController
 */

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/{id}")
    public Goods getGoodsById(@PathVariable String id) {
        return goodsService.getGoodsById(id);
    }

    @RequestMapping("/list")
    public IPage<Goods> list(Page<Goods> page, Goods goods) {
        return goodsService.list(page, goods);
    }

    @RequestMapping("/find")
    public List<Goods> find(Goods goods) {
        return goodsService.find(goods);
    }

}

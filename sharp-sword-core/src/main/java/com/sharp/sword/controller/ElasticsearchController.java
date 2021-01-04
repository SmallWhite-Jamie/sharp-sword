package com.sharp.sword.controller;

import com.sharp.sword.bean.ESGuShiEntity;
import com.sharp.sword.util.api.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author lizheng
 * @date: 16:22 2019/10/25
 * @Description: ElasticsearchController
 */
@RestController
@RequestMapping("search")
public class ElasticsearchController {


    @RequestMapping("/add")
    public ApiResult search(ESGuShiEntity gushi) throws IOException {

        return ApiResult.ok();
    }
}

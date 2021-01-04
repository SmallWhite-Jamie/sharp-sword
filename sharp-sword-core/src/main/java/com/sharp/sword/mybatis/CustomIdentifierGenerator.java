package com.sharp.sword.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.sharp.sword.idgenerator.IdGenerator;

/**
 * @author lizheng
 * @date: 22:14 2020/02/13
 * @Description: CustomIdentifierGenerator
 */
public class CustomIdentifierGenerator implements IdentifierGenerator {

    private IdGenerator idGenerator;

    public CustomIdentifierGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Number nextId(Object entity) {
        return idGenerator.nextId();
    }
}

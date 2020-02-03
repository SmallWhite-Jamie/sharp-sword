package com.jamie.framework.mybatis.handler;

import com.jamie.framework.mybatis.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lizheng
 * @date: 20:43 2020/02/01
 * @Description: EnumValueTypeHandler
 */
@MappedTypes({BaseEnum.class})
public class EnumValueTypeHandler<T extends BaseEnum>  extends BaseTypeHandler<T> {

    private Class<T> type;

    public EnumValueTypeHandler(Class<T> type) {
        if(type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.type = type;
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if(jdbcType == null) {
            ps.setInt(i, parameter.getValue());
        } else {
            ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int anInt = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return getEnumByValue(anInt);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getEnumByValue(rs.getInt(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getEnumByValue(cs.getInt(columnIndex));
    }


    private T getEnumByValue(int anInt) {
        T[] enumConstants = type.getEnumConstants();
        for (T baseEnum : enumConstants) {
            if (baseEnum.getValue() == anInt) {
                return baseEnum;
            }
        }
        return null;
    }
}

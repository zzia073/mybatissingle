package com.study.typehandler;

import com.study.ienum.IEnum;
import com.study.ienum.SCORE;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author ：fei
 * @date ：Created in 2019/10/11 0011 15:21
 * TypeHandler在注册时调用TypeHandlerRegistry#register方法
 * 该方法会根据MappedTypes注解是否有值去判定调用那个register方法
 * 两个方法都会调用TypeHandlerRegistry#getInstance方法获取TypeHandler
 * 当有MappedTypes注解时会调用有参构造方法参数类型为Class，
 * 没有该注解时会调用无参构造方法
 */
@MappedTypes({SCORE.class})
public class IEnumTypeHandler<E extends Enum<E> & IEnum<E>> extends BaseTypeHandler<E> {
    private Class<E> type;
    private E[] enums;
    public IEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = this.type.getEnumConstants();
        if(this.enums==null){
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, ((IEnum)parameter).getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getEnum(rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getEnum(rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getEnum(cs.getInt(columnIndex));
    }

    private E getEnum(Integer s) {
        return Arrays.stream(enums)
                .filter(e -> e.getCode().equals(s))
                .findFirst()
                .orElse(null);
    }
}

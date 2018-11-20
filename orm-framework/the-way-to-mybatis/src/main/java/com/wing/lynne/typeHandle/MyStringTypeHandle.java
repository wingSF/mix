package com.wing.lynne.typeHandle;

import com.wing.lynne.constant.Constant;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 重写自己的typeHandle的方式
 * 第一实现TypeHandle接口
 * 第二集成BaseTypeHandle类
 */
//public class MyStringTypeHandle implements TypeHandler {

//}
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MyStringTypeHandle extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, Constant.STRING_TYPE_PREFIX + parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName) + Constant.STRING_TYPE_SUFFIX;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex) + Constant.STRING_TYPE_SUFFIX;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex) + Constant.STRING_TYPE_SUFFIX;
    }
}

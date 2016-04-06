package com.zizaike.passport.dao.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.zizaike.entity.passport.domain.LoginType;

/**
 * ClassName:UserTypeHandler <br/>
 * Function: 登陆类型handler. <br/>
 * Reason: handler. <br/>
 * Date: 2015年11月4日 下午9:49:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public class LoginTypeHandler extends BaseTypeHandler<LoginType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LoginType parameter, JdbcType jdbcType)
            throws SQLException {

        ps.setString(i, parameter.getType());

    }

    @Override
    public LoginType getNullableResult(ResultSet rs, String columnName) throws SQLException {

        String value = rs.getString(columnName);
        return LoginType.findByValue(value);
    }

    @Override
    public LoginType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        String value = rs.getString(columnIndex);
        return LoginType.findByValue(value);
    }

    @Override
    public LoginType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        String value = cs.getString(columnIndex);
        return LoginType.findByValue(value);
    }

}

package com.zizaike.passport.dao.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.zizaike.entity.passport.domain.UpdatePasswordOpreateType;

/**
 * ClassName:UserTypeHandler <br/>
 * Function: 更新密码操作类型handler. <br/>
 * Reason: handler. <br/>
 * Date: 2015年11月4日 下午9:49:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public class UpdatePasswordOpreateTypeHandler extends BaseTypeHandler<UpdatePasswordOpreateType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UpdatePasswordOpreateType parameter, JdbcType jdbcType)
            throws SQLException {

        ps.setString(i, parameter.getType());

    }

    @Override
    public UpdatePasswordOpreateType getNullableResult(ResultSet rs, String columnName) throws SQLException {

        String value = rs.getString(columnName);
        return UpdatePasswordOpreateType.findByValue(value);
    }

    @Override
    public UpdatePasswordOpreateType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        String value = rs.getString(columnIndex);
        return UpdatePasswordOpreateType.findByValue(value);
    }

    @Override
    public UpdatePasswordOpreateType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        String value = cs.getString(columnIndex);
        return UpdatePasswordOpreateType.findByValue(value);
    }

}

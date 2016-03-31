package com.zizaike.passport.dao.handler;  

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.zizaike.entity.passport.domain.PassportStatus;

/**  
 * ClassName:PassportStatusHandler <br/>  
 * Function: passport状态类型handler. <br/>  
 * Reason:  handler. <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class PassportStatusHandler extends BaseTypeHandler<PassportStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PassportStatus parameter,
        JdbcType jdbcType) throws SQLException {

      ps.setInt(i, parameter.getValue());

    }

    @Override
    public PassportStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {

      int value = rs.getInt(columnName);
      return PassportStatus.findByValue(value);
    }

    @Override
    public PassportStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

      int value = rs.getInt(columnIndex);
      return PassportStatus.findByValue(value);
    }

    @Override
    public PassportStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

      int value = cs.getInt(columnIndex);
      return PassportStatus.findByValue(value);
    }

}
  

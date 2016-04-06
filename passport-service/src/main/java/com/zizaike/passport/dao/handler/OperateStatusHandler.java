package com.zizaike.passport.dao.handler;  

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.zizaike.entity.passport.domain.OperateStatus;

/**  
 * ClassName:OperateStatusHandler <br/>  
 * Function: 操作状态handler. <br/>  
 * Reason:  handler. <br/>  
 * Date:     2015年11月4日 下午9:49:43 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class OperateStatusHandler extends BaseTypeHandler<OperateStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OperateStatus parameter,
        JdbcType jdbcType) throws SQLException {

      ps.setInt(i, parameter.getValue());

    }

    @Override
    public OperateStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {

      int value = rs.getInt(columnName);
      return OperateStatus.findByValue(value);
    }

    @Override
    public OperateStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

      int value = rs.getInt(columnIndex);
      return OperateStatus.findByValue(value);
    }

    @Override
    public OperateStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

      int value = cs.getInt(columnIndex);
      return OperateStatus.findByValue(value);
    }

}
  

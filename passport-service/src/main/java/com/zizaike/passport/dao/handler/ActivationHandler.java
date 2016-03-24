package com.zizaike.passport.dao.handler;  

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.zizaike.entity.passport.domain.Activation;

/**  
 * ClassName:ActivationHandler <br/>  
 * Function: 激活handler. <br/>  
 * Reason:  handler. <br/>  
 * Date:     2015年11月4日 下午9:49:43 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class ActivationHandler extends BaseTypeHandler<Activation> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Activation parameter,
        JdbcType jdbcType) throws SQLException {

      ps.setInt(i, parameter.getValue());

    }

    @Override
    public Activation getNullableResult(ResultSet rs, String columnName) throws SQLException {

      int value = rs.getInt(columnName);
      return Activation.findByValue(value);
    }

    @Override
    public Activation getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

      int value = rs.getInt(columnIndex);
      return Activation.findByValue(value);
    }

    @Override
    public Activation getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

      int value = cs.getInt(columnIndex);
      return Activation.findByValue(value);
    }

}
  

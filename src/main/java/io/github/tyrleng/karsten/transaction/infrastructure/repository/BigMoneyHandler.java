package io.github.tyrleng.karsten.transaction.infrastructure.repository;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class BigMoneyHandler extends BaseTypeHandler<BigMoney> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int index, BigMoney bigMoney, JdbcType jdbcType) throws SQLException {
        ps.setString(index,bigMoney.toString());
    }

    @Override
    public BigMoney getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String currencyWithAmount = resultSet.getString(columnName);
        String[] split = currencyWithAmount.split(" ");
        CurrencyUnit currencyUnit = CurrencyUnit.of(split[0]);
        BigDecimal amount = new BigDecimal(split[1]);
        return BigMoney.of(currencyUnit,amount);
    }

    @Override
    public BigMoney getNullableResult(ResultSet resultSet, int index) throws SQLException {
        String currencyWithAmount = resultSet.getString(index);
        String[] split = currencyWithAmount.split(" ");
        CurrencyUnit currencyUnit = CurrencyUnit.of(split[0]);
        BigDecimal amount = new BigDecimal(split[1]);
        return BigMoney.of(currencyUnit,amount);
    }

    @Override
    public BigMoney getNullableResult(CallableStatement cs, int index) throws SQLException {
        String currencyWithAmount = cs.getString(index);
        String[] split = currencyWithAmount.split(" ");
        CurrencyUnit currencyUnit = CurrencyUnit.of(split[0]);
        BigDecimal amount = new BigDecimal(split[1]);
        return BigMoney.of(currencyUnit,amount);
    }
}

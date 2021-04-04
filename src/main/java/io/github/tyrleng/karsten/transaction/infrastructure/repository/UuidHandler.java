package io.github.tyrleng.karsten.transaction.infrastructure.repository;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UuidHandler extends BaseTypeHandler<UUID> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int index, UUID uuid, JdbcType jdbcType) throws SQLException {
        ps.setString(index, uuid.toString());
    }

    @Override
    public UUID getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return UUID.fromString(resultSet.getString(columnName));
    }

    @Override
    public UUID getNullableResult(ResultSet resultSet, int index) throws SQLException {
        return UUID.fromString(resultSet.getString(index));
    }

    @Override
    public UUID getNullableResult(CallableStatement cs, int index) throws SQLException {
        return UUID.fromString(cs.getString(index));
    }
}

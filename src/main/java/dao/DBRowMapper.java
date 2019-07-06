package dao;

import java.sql.ResultSet;

public interface DBRowMapper<D> {
    D mapRow(ResultSet rs);
}

package database.mapper;

import java.sql.ResultSet;

public interface DBRowMapper<D> {
    D mapRow(ResultSet rs);
}

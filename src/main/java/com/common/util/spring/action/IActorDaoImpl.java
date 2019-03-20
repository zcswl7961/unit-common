package com.common.util.spring.action;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by zhoucg on 2019-03-19.
 */
public class IActorDaoImpl implements IActorDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(String firstName, String lastName) {
        jdbcTemplate.update("insert into actor(first_name,last_name,last_update) VALUES (?,?,?)",
                new Object[]{firstName,lastName,"zcg"},
                new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,});

    }

    @Override
    public String query(String firstName) {
        jdbcTemplate.update("update actor set last_name='nihao' where first_name = ?",
                new Object[]{firstName},
                new int[]{Types.VARCHAR});
        return null;
    }
}

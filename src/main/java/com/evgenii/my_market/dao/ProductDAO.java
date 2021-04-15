package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.Parameters;
import com.evgenii.my_market.entity.Product;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDAO extends AbstractJpaDao<Product>{
}

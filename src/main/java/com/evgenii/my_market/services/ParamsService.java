package com.evgenii.my_market.services;


import com.evgenii.my_market.dao.ParamsDAO;
import com.evgenii.my_market.dao.ProductDAO;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.entity.Parameters;
import com.evgenii.my_market.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParamsService {
   private final ParamsDAO dao;

    public List<Parameters> getAllParams() {


        return dao.pagfindAll();
    }

    public Parameters save(Parameters newParameters) {
       return dao.saveNewCategory(newParameters);
    }
}

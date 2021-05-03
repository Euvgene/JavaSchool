package com.evgenii.my_market.service;


import com.evgenii.my_market.dao.ParamsDAO;
import com.evgenii.my_market.entity.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParamsService {
   private final ParamsDAO dao;

    public List<Parameters> findAllParams() {
        return dao.findAllParams();
    }

    public Parameters save(Parameters newParameters) {
       return dao.saveNewCategory(newParameters);
    }
}

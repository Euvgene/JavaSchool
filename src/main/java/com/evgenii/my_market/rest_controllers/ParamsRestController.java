package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.entity.Parameters;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.services.ParamsService;
import com.evgenii.my_market.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/params")
@RequiredArgsConstructor
public class ParamsRestController {

    private final ParamsService paramsService;

    @GetMapping()
    public List<Parameters> pagination() {
        return paramsService.getAllParams();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Parameters saveCategory(@RequestBody Parameters newParameters) {
      return   paramsService.save(newParameters);
    }

}

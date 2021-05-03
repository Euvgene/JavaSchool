package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.entity.Parameters;
import com.evgenii.my_market.service.ParamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/params")
@RequiredArgsConstructor
public class ParamsRestController {

    private final ParamsService paramsService;

    @GetMapping()
    public List<Parameters> findAllParams() {
        return paramsService.findAllParams();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Parameters saveCategory(@RequestBody Parameters newParameters) {
      return   paramsService.save(newParameters);
    }

}

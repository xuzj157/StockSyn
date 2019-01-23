package personal.xuzj157.stocksyn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import personal.xuzj157.stocksyn.service.BasicService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/basic")
public class BasicController {

    @Resource
    BasicService basicService;

    @GetMapping("/init")
    public void init(){
        basicService.initBasic();
    }

    @GetMapping("/ori2First")
    public void ori2First(@RequestParam int days){
        basicService.ori2First(days);
    }

    @GetMapping("/first2Second")
    public void first2Second(){
        basicService.first2Second();
    }
}

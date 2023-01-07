package com.podosoft.zenela.Controllers.RestControllers;

import com.podosoft.zenela.Dto.Responses.SearchResponse;
import com.podosoft.zenela.Services.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private final MainService mainService;

    public UserRestController(MainService mainService) {
        this.mainService = mainService;
    }


    @GetMapping("/search")
    public SearchResponse search(@RequestParam("q") String query){

        SearchResponse searchResponse = mainService.searchPeople(query);

        return searchResponse;
    }








}

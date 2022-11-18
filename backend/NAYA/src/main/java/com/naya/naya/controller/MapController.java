package com.naya.naya.controller;

import com.naya.naya.dto.MapDto;
import com.naya.naya.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

@Controller
@ResponseBody
@RequestMapping("/map")
@RequiredArgsConstructor
@Slf4j
public class MapController {

    private final MapService mapService;

    @GetMapping
    public MapDto search(@RequestParam("address") String address) throws MalformedURLException, UnsupportedEncodingException {
        return mapService.search(address);
    }
}

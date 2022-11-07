package com.naya.naya.controller;

import com.naya.naya.dto.Request.NayaCardRqDto2;
import com.naya.naya.service.NayaCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
//@RestController
@Controller
@ResponseBody
@RequestMapping("/card")
@RequiredArgsConstructor
@Slf4j
public class NayaCardController {

    private final NayaCardService nayaCardService;

    @GetMapping("/detail")
    public NayaCardRqDto2 findByNayaCardId(@RequestParam("id") Long cardId){
        log.debug("nayaCardController findByNayaCardId method, parameter Long, nayaCardId: " + cardId);
        return nayaCardService.findByNayaCardId(cardId);
    }

    @GetMapping
    public List<NayaCardRqDto2> findAllByUserId(@RequestParam("id") String userId){
        log.debug("nayaCardController findAllByUserId method, parameter Long, userId " + userId);
        return nayaCardService.findAllByUserId(userId);
    }

    @PostMapping
    public List<NayaCardRqDto2> save(@RequestBody NayaCardRqDto2 dto){
        log.debug("nayaCardController save method, parameter NayaCardRequestDto, dto " + dto);
        return nayaCardService.save(dto);
    }

    @PutMapping
    public NayaCardRqDto2 update(@RequestBody NayaCardRqDto2 dto){
        log.debug("nayaCardController update method, parameter NayaCardRequestDto, dto " + dto);
        return nayaCardService.update(dto);
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable Long id){
        nayaCardService.delete(id);
        log.debug("nayaCardController delete method, parameter Long, nayaCardId " + id);
        return true;
    }
}

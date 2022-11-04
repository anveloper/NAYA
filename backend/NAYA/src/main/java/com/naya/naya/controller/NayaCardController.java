package com.naya.naya.controller;

import com.naya.naya.dto.Request.NayaCardRqDto2;
import com.naya.naya.service.NayaCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
//@RestController
@Controller
@ResponseBody
@RequestMapping("/card")
@RequiredArgsConstructor
public class NayaCardController {

    private final NayaCardService nayaCardService;

    @GetMapping("/detail")
    public NayaCardRqDto2 findByNayaCardId(@RequestParam("id") Long cardId){
        return nayaCardService.findByNayaCardId(cardId);
    }

    @GetMapping
    public List<NayaCardRqDto2> findAllByUserId(@RequestParam("id") Long userId){
        return nayaCardService.findAllByUserId(userId);
    }

    @PostMapping
    public List<NayaCardRqDto2> save(@RequestBody NayaCardRqDto2 dto){
        return nayaCardService.save(dto);
    }

    @PutMapping
    public NayaCardRqDto2 update(@RequestBody NayaCardRqDto2 dto){
        return nayaCardService.update(dto);
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable Long id){
        nayaCardService.delete(id);
        return true;
    }
}

package com.naya.naya.service;

import com.naya.naya.dto.Request.NayaCardRqDto2;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface NayaCardService {

    NayaCardRqDto2 findByNayaCardId(Long nayaCardId);
    List<NayaCardRqDto2> findAllByUserId(Long userId);
    List<NayaCardRqDto2> save(NayaCardRqDto2 dto);
    NayaCardRqDto2 update(@RequestBody NayaCardRqDto2 dto);
    void delete(Long nayaCardId);
}

package com.naya.naya.service;

import com.naya.naya.dto.SendCardDto;

public interface SendCardService {

    SendCardDto save(SendCardDto dto);

    String findBySendCardId(long sendCardId);
}

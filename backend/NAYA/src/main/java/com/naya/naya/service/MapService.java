package com.naya.naya.service;

import com.naya.naya.dto.MapDto;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

public interface MapService {
    MapDto search(String address) throws MalformedURLException, UnsupportedEncodingException;
}

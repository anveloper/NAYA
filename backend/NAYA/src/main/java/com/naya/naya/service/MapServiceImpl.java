package com.naya.naya.service;

import com.naya.naya.dto.MapDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapServiceImpl implements MapService{

    @Override
    public MapDto search(String address) throws MalformedURLException, UnsupportedEncodingException {

        log.debug("mapService search method, parameter address : "+address);

        String addr= URLEncoder.encode(address,"utf-8");
        String api="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+addr;
        StringBuffer sb=new StringBuffer();
        MapDto res=new MapDto();

        try {
            URL url=new URL(api);
            HttpsURLConnection http= (HttpsURLConnection) url.openConnection();
            http.setRequestProperty("Content-Type","application/json");
            http.setRequestProperty("X-NCP-APIGW-API-KEY-ID","9yl24643ku");
            http.setRequestProperty("X-NCP-APIGW-API-KEY","TO4U41iKFZxxXO8hcOa07tmpYbcvS8zNwlLgCrui");
            http.setRequestMethod("GET");
            http.connect();

            InputStreamReader in=new InputStreamReader(http.getInputStream(),"utf-8");
            BufferedReader br=new BufferedReader(in);
            String line;
            while((line= br.readLine())!=null){
                sb.append(line).append("\n");
            }

            JSONParser parser=new JSONParser();
            JSONObject jsonObject= (JSONObject) parser.parse(sb.toString());
            JSONObject jsonObject2;
            JSONArray jsonArray= (JSONArray) jsonObject.get("addresses");
            String x="";
            String y="";
            String roadAddress="";
            String jibunAddress="";

            for(int i=0;i<jsonArray.size();i++){

                jsonObject2= (JSONObject) jsonArray.get(i);

                if(jsonObject2.get("x")!=null)
                    x=jsonObject2.get("x").toString();

                if(jsonObject2.get("y")!=null)
                    y=jsonObject2.get("y").toString();

                if(jsonObject2.get("roadAddress")!=null)
                    roadAddress=jsonObject2.get("roadAddress").toString();

                if(jsonObject2.get("jibunAddress")!=null)
                    jibunAddress=jsonObject2.get("jibunAddress").toString();
            }

            br.close();
            in.close();
            http.disconnect();

            res.setX(x); res.setY(y); res.setRoadAddress(roadAddress); res.setJibunAddress(jibunAddress);

        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }
}

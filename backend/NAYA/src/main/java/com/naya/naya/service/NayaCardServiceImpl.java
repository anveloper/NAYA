package com.naya.naya.service;

import com.naya.naya.dto.DesignDto;
import com.naya.naya.dto.NayaCardDto;
import com.naya.naya.dto.Request.NayaCardRqDto2;
import com.naya.naya.entity.Design;
import com.naya.naya.entity.NayaCard;
import com.naya.naya.repository.DesignRepository;
import com.naya.naya.repository.NayaCardRepository;
import com.naya.naya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NayaCardServiceImpl implements NayaCardService{

    private final UserRepository userRepository;
    private final NayaCardRepository nayaCardRepository;
    private final DesignRepository designRepository;
    // 기본 글자 폰트
    private final String textFont = "바탕체";
    // 기본 글자 크기 10
    private final int defaultTextSize = 10;
    // r,g,b 가 모두 0이면 검은색
    private final int defaultTextColorR = 0;
    private final int defaultTextColorG = 0;
    private final int defaultTextColorB = 0;
    // nayaCard 컬럼들의 기본 위치 저장, 14개
    // 예시 100 x 100 기준 { x, y }, 글자 크기 10 적용
    private final double[][] defaultRowColumn = {
            { 10, 85 }, // Design 테이블 기준 column_no = 1, 주소
            { 0, 0 }, // Design 테이블 기준 column_no = 2, 배경
            { 10, 35 }, // Design 테이블 기준 column_no = 3, 회사명, 왼쪽
            { 10, 45 }, // Design 테이블 기준 column_no = 4, 이메일, 왼쪽
            { 60, 55 }, // Design 테이블 기준 column_no = 5, 추가정보1, 오른쪽
            { 60, 65 }, // Design 테이블 기준 column_no = 6, 추가정보2, 오른쪽
            { 60, 75 }, // Design 테이블 기준 column_no = 7, 추가정보3, 오른쪽
            { 60, 35 }, // Design 테이블 기준 column_no = 8, 팩스번호, 오른쪽
            { 40, 25 }, // Design 테이블 기준 column_no = 9, 로고, 중간
            { 10, 85 }, // Design 테이블 기준 column_no = 10, 연락처, 왼쪽
            { 10, 75 }, // Design 테이블 기준 column_no = 11, 이름, 왼쪽
            { 10, 55 }, // Design 테이블 기준 column_no = 12, 직책, 왼쪽
            { 10, 65 }, // Design 테이블 기준 column_no = 13, 부서명, 왼쪽
            { 10, 45 }, // Design 테이블 기준 column_no = 14, 일반전화, 오른쪽
    };

    private NayaCard findById(Long id){
        log.debug("nayaCardService findById method, parameter Long, nayaCardId " + id);
        return nayaCardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public NayaCardRqDto2 findByNayaCardId(Long nayaCardId) {

        log.debug("nayaCardService findByNayaCardId method, parameter Long, nayaCardId " + nayaCardId);

        return NayaCardRqDto2.from(NayaCardDto.of(findById(nayaCardId),
                designRepository.findByNayaCardId(nayaCardId)
                        .stream()
                        .map(DesignDto::from)
                        .collect(Collectors.toList())
                ));
    }

    @Override
    public List<NayaCardRqDto2> findAllByUserId(Long userId) {

        log.debug("nayaCardService findAllByUserId method, parameter Long, userId " + userId);

        return nayaCardRepository.findByUserId(userId)
                .stream()
                .map(NayaCardDto::from)
                .map(n -> findByNayaCardId(n.getNayaCardId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<NayaCardRqDto2> save(NayaCardRqDto2 dto) {

        log.debug("nayaCardService save method, parameter NayaCardRqDto, dto " + dto);

        // 카드 내용 저장
        NayaCardDto nayaCardDto = NayaCardDto.from(
                nayaCardRepository.save(
                        NayaCard.create(NayaCardDto.from(dto))));

        // 카드 추가 시에 디폴트 값 or 넘어온 값을 넣어줘야 함
        // 기본 14개 생성
        Queue<Integer> columnNos = new LinkedList<>();
        for(DesignDto cur : dto.getDesignList()) columnNos.add(cur.getColumnNo());

        Design cur;
        DesignDto designDto;
        for(int i=1;i<=14;i++){
            // 설정 값이 있다면
            if(columnNos.contains(i)){
                dto.getDesignList().get(i-1).setNayaCardId(nayaCardDto.getNayaCardId());
                cur = Design.create(dto.getDesignList().get(i-1),i);
            }
            // 설정 값이 없다면
            else {

                cur = Design.create(DesignDto.of(nayaCardDto.getNayaCardId(), i, textFont, defaultTextSize,
                        defaultTextColorR, defaultTextColorG, defaultTextColorB,
                        defaultRowColumn[i-1][0], defaultRowColumn[i-1][1]));

            }
            designRepository.save(cur);
        }

        return findAllByUserId(dto.getUserId());
    }

    // 2022.10.30 20:33 issue
    // @Transactional 안붙이면 select만 실행됨( update 구문 실행 안됨)
    @Transactional
    @Override
    public NayaCardRqDto2 update(NayaCardRqDto2 dto) {

        log.debug("nayaCardService update method, parameter NayaCardRqDto, dto " + dto);

        // dto -> update
        // 카드 정보 변경
        nayaCardRepository.findById(dto.getNayaCardId())
                .orElseThrow(IllegalAccessError::new)
                .update(NayaCardDto.from(dto));

        // 디자인 정보 변경
        Queue<Integer> columnNos = new LinkedList<>();
        // 입력 값이 비어있다면 기존 값 그대로 저장
        for(DesignDto cur : dto.getDesignList()) columnNos.add(cur.getColumnNo());
        List<Design> list = designRepository.findByNayaCardId(dto.getNayaCardId());
        for(Design cur : list) System.out.println("cur: "+ cur);
        Design cur;
        int idx = 0;

        for(int i=1; i <= 14; i++){
            // 변경 값이 넘어왔다면
            if(columnNos.contains(i)){
                list.get(i-1).
                        update(
                                dto.getDesignList().
                                        get(idx++));
            }
        }
        return findByNayaCardId(dto.getNayaCardId());
    }

    @Transactional
    @Override
    public void delete(Long nayaCardId) {

        log.debug("nayaCardService delete method, parameter Long, Long " + nayaCardId);

        NayaCard nayaCard = nayaCardRepository.findById(nayaCardId).orElseThrow(IllegalAccessError::new);
        List<Design> list = designRepository.findByNayaCardId(nayaCardId);

        designRepository.deleteByNayaCardId(nayaCardId);
        nayaCardRepository.deleteById(nayaCardId);

    }
}

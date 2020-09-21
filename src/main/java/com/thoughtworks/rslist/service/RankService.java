package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Trade;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.Tradedto;
import com.thoughtworks.rslist.repository.Rankrepository;
import com.thoughtworks.rslist.repository.RsEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RankService {

    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    Rankrepository rankrepository;

    public ResponseEntity<Trade> BuyRank(Tradedto tradedto) {

        Optional<RsEventDto> OldrsEvent = rsEventRepository.findById(tradedto.getEventId());
        if (!OldrsEvent.isPresent()) {
            return ResponseEntity.status(400).build();
        }

        Optional<RsEventDto> newEvent = rsEventRepository.findByRank(tradedto.getRank());


        RsEventDto rsEventdto = OldrsEvent.get();


        if (rsEventdto.getAmount() >= tradedto.getAmount())
            return ResponseEntity.status(400).build();


        if (newEvent.isPresent())
            rsEventRepository.deleteAllByRank(tradedto.getRank());


        rsEventdto.setAmount(tradedto.getAmount());
        rsEventdto.setRank(tradedto.getRank());

        rsEventRepository.save(rsEventdto);
        Tradedto result = rankrepository.save(tradedto);


        Trade trade = Trade.builder()
                .eventId(result.getEventId())
                .rank(result.getRank())
                .amount(result.getAmount())
                .build();


        return ResponseEntity.ok().body(trade);

    }

    public List<Trade> findAll() {
        List<Trade> tradeList = rankrepository.findAll().stream()
                .map(item ->
                        Trade.builder()
                                .id(item.getId())
                                .amount(item.getAmount())
                                .eventId(item.getEventId())
                                .rank(item.getRank())
                                .build()).collect(Collectors.toList());
        return tradeList;
    }
}

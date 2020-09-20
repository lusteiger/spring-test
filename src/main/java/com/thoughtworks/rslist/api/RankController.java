package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Trade;
import com.thoughtworks.rslist.dto.Tradedto;
import com.thoughtworks.rslist.repository.Rankrepository;
import com.thoughtworks.rslist.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankController {

    @Autowired
    RankService rankService;

    @PostMapping("/rs/rank/buy")
    public ResponseEntity<Trade> PurchaseRank(@RequestBody Trade trade) {
        Tradedto tradedto = Tradedto.builder()
                .amount(trade.getAmount())
                .rank(trade.getRank())
                .eventId(trade.getEventId())
                .build();
        return rankService.BuyRank(tradedto);
    }

    @GetMapping("/rs/rank/list")
    public ResponseEntity<List<Trade>> PurchaseList() {
        List<Trade> tradeList = rankService.findAll();
        return ResponseEntity.ok().body(tradeList);
    }
}

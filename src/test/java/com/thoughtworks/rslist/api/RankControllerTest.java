package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.Trade;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RankControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        voteRepository.deleteAll();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        userDto =
                UserDto.builder()
                        .voteNum(10)
                        .phone("188888888888")
                        .gender("female")
                        .email("a@b.com")
                        .age(19)
                        .userName("idolice")
                        .build();
    }


    @Test
    public void shouldPurchaseRangeWithNoOnePurchaseSuccess() throws Exception {
        Trade trade = Trade.builder()
                .amount(100)
                .rank(1)
                .eventId(1)
                .build();

        RsEventDto rsEventDto = RsEventDto.builder()
                .eventName("事件1")
                .keyword("娱乐")
                .build();

        rsEventRepository.save(rsEventDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(trade);
        mockMvc.perform(post("/rs/rank/buy").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/rank/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].rank", is(trade.getRank())))
                .andExpect(jsonPath("[0].amount", is(trade.getAmount())));

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].rank", is(trade.getRank())))
                .andExpect(jsonPath("[0].amount", is(trade.getAmount())));

    }


    @Test
    public void shouldPurchaseRangeWithHasOnePurchaseSuccess() throws Exception {
        Trade trade = Trade.builder()
                .amount(300)
                .rank(1)
                .eventId(1)
                .build();

        RsEventDto rsEventDto1 = RsEventDto.builder()
                .eventName("事件2")
                .keyword("娱乐")
                .amount(100)
                .rank(2)
                .build();

        RsEventDto rsEventDto2 = RsEventDto.builder()
                .eventName("事件1")
                .keyword("哈哈")
                .rank(1)
                .amount(99)
                .build();

        rsEventRepository.save(rsEventDto1);
        rsEventRepository.save(rsEventDto2);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(trade);
        mockMvc.perform(post("/rs/rank/buy").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/rank/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].rank", is(trade.getRank())))
                .andExpect(jsonPath("[0].amount", is(trade.getAmount())));

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].rank", is(trade.getRank())))
                .andExpect(jsonPath("[0].amount", is(trade.getAmount())));

    }



    @Test
    public void shouldFailedUseEqualsAmount() throws Exception {
        Trade trade = Trade.builder()
                .amount(100)
                .rank(1)
                .eventId(1)
                .build();

        RsEventDto rsEventDto = RsEventDto.builder()
                .eventName("事件1")
                .keyword("娱乐")
                .amount(100)
                .rank(2)
                .build();

        rsEventRepository.save(rsEventDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(trade);
        mockMvc.perform(post("/rs/rank/buy").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }


    @Test
    public void shouldFailedUselessAmount() throws Exception {
        Trade trade = Trade.builder()
                .amount(99)
                .rank(1)
                .eventId(1)
                .build();

        RsEventDto rsEventDto = RsEventDto.builder()
                .eventName("事件1")
                .keyword("娱乐")
                .amount(100)
                .rank(2)
                .build();

        rsEventRepository.save(rsEventDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(trade);
        mockMvc.perform(post("/rs/rank/buy").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

}
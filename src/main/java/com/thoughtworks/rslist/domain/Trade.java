package com.thoughtworks.rslist.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Trade {
    private int id;
    private int amount;
    private int rank;
    private int eventId;

}

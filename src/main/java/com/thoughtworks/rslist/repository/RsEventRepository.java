package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsEventDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RsEventRepository extends CrudRepository<RsEventDto, Integer> {
    List<RsEventDto> findAll();

    List<RsEventDto> findAllByOrderByRankAsc();

    Optional<RsEventDto> findByRank(int rank);

    @Transactional
    void deleteAllByRank(int rank);

    @Transactional
    void deleteAllByUserId(int userId);
}

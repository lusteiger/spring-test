package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.Tradedto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rankrepository extends CrudRepository<Tradedto,Integer> {
        Tradedto save(Tradedto tradedto);
        List<Tradedto> findAll();
}

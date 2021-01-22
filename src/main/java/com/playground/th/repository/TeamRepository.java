package com.playground.th.repository;

import com.playground.th.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long > {

    List<Team> findByNameContains(String name);
}

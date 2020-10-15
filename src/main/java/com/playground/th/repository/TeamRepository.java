package com.playground.th.repository;

import com.playground.th.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long > {

}

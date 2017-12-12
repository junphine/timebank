package com.blockchain.timebank.service;

import com.blockchain.timebank.entity.TeamUserEntity;

import java.util.List;

public interface TeamUserService {
    TeamUserEntity addUserToTeam(TeamUserEntity teamUser);

    List<TeamUserEntity> findAll();
}

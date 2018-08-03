package com.pcms.service;

import com.pcms.domain.Moive;

import java.util.List;
import java.util.Map;

public interface MoiveService {

    int insert(Moive record);

    List<Moive> searchMoiveByParam(Map param);
}

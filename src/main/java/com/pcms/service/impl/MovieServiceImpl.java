package com.pcms.service.impl;

import com.pcms.dao.MoiveMapper;
import com.pcms.domain.Moive;
import com.pcms.service.MoiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("movieService")
public class MovieServiceImpl implements MoiveService {

    @Autowired
    private MoiveMapper moiveMapper;

    public  int insert(Moive record){
       return  moiveMapper.insert(record);
    }

    public List<Moive> searchMoiveByParam(Map param){
        return moiveMapper.searchMoiveByParam(param);
    }
}

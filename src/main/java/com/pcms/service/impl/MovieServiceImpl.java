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

    public List<Moive> searchMoiveForWXByParam(Map param){
        return moiveMapper.searchMoiveForWXByParam(param);
    }

    public Moive getMoiveByParam(Map param){
        return moiveMapper.getMoiveByParam(param);
    }


    public int getMoiveCountByParam(Map param){
        return moiveMapper.getMoiveCountByParam(param);
    }

    public int updateByPrimaryKeySelective(Moive record){
        return moiveMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByMoiveName(Moive record){
        return moiveMapper.updateByMoiveName(record);
    }

   public int insertSelective(Moive record){
       return moiveMapper.insertSelective(record);
   }

   public Moive selectByPrimaryKey(Long id){
       return moiveMapper.selectByPrimaryKey(id);
   }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return moiveMapper.deleteByPrimaryKey(id);
    }


}

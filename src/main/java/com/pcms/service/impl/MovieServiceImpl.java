package com.pcms.service.impl;

import com.pcms.dao.MoiveFailMapper;
import com.pcms.dao.MoiveMapper;
import com.pcms.dao.RequestMoiveMapper;
import com.pcms.domain.Moive;
import com.pcms.domain.MoiveFail;
import com.pcms.domain.RequestMoive;
import com.pcms.service.FileService;
import com.pcms.service.MoiveService;
import com.pcms.util.DateUtil;
import com.pcms.util.PcmsConst;
import com.pcms.util.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("movieService")
public class MovieServiceImpl implements MoiveService {

    @Autowired
    private MoiveMapper moiveMapper;

    @Autowired
    private RequestMoiveMapper requestMoiveMapper;

    @Autowired
    private MoiveFailMapper moiveFailMapper;

    @Autowired
    private FileService fileService;

    public int insert(Moive record) {
        return moiveMapper.insert(record);
    }

    public List<Moive> searchMoiveByParam(Map param) {
        return moiveMapper.searchMoiveByParam(param);
    }

    public List<Moive> searchMoiveForWXByParam(Map param) {
        return moiveMapper.searchMoiveForWXByParam(param);
    }

    public Moive getMoiveByParam(Map param) {
        return moiveMapper.getMoiveByParam(param);
    }


    public int getMoiveCountByParam(Map param) {
        return moiveMapper.getMoiveCountByParam(param);
    }

    public int updateByPrimaryKeySelective(Moive record) {
        return moiveMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByMoiveName(Moive record) {
        return moiveMapper.updateByMoiveName(record);
    }

    public int insertSelective(Moive record) {
        return moiveMapper.insertSelective(record);
    }

    public Moive selectByPrimaryKey(Long id) {
        return moiveMapper.selectByPrimaryKey(id);
    }

    public int updateMoviveFail(MoiveFail moiveFail) {
        return moiveFailMapper.updateByPrimaryKeySelective(moiveFail);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return moiveMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertMoiveFail(MoiveFail moiveFail) {
        return moiveFailMapper.insertSelective(moiveFail);
    }

    @Override
    public int getMoiveFailCountByParam(Map param) {

        return moiveFailMapper.getMoiveFailCountByParam(param);
    }

    @Override
    public List<MoiveFail> searchMoiveFailByParam(Map param) {
        return moiveFailMapper.searchMoiveFailByParam(param);
    }
    @Override
    public MoiveFail getMoiveFailByParam(Map param) {
        return moiveFailMapper.getMoiveFailByParam(param);
    }

    @Override
    public int insertRequestMoive(RequestMoive requestMoive) {
        return requestMoiveMapper.insertSelective(requestMoive);
    }

    @Override
    public int getMoiveRequestCountByParam(Map param) {
        return requestMoiveMapper.getMoiveRequestCountByParam(param);
    }

    @Override
    public List<RequestMoive> MoiveRequestByParam(Map param) {
        return requestMoiveMapper.MoiveRequestByParam(param);
    }

    @Override
    public boolean parseExcelFile(File file) {

        ArrayList<ArrayList<String>> arrayLists = fileService.parseExcelFile(file);

        if (arrayLists == null || arrayLists.size() == 0) {
            return false;
        }

        for (int i = 0; i < arrayLists.size(); i++) {
            ArrayList arrayList = arrayLists.get(i);
            Moive moive = new Moive();
            long id = Long.parseLong(RandomNumber.randomKey(6));
            moive.setId(id);
            moive.setPanurl(arrayList.get(1) + "");
            moive.setPanpwd(arrayList.get(2) + "");
            moive.setMname(arrayList.get(0) + "");
            moive.setMurl(PcmsConst.url + id + ".html");
            moive.setUpdatetime(DateUtil.getCurTimestamp());
            this.insert(moive);
            Map param = new HashMap();
            param.put("moive", moive);
            fileService.genFile(param);
        }

        return true;
    }


}

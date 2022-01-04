package top.nzhz.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.nzhz.wiki.domain.Music;
import top.nzhz.wiki.domain.MusicExample;
import top.nzhz.wiki.mapper.MusicMapper;
import top.nzhz.wiki.req.MusicQueryReq;
import top.nzhz.wiki.req.MusicSaveReq;
import top.nzhz.wiki.resp.MusicQueryResp;
import top.nzhz.wiki.resp.PageResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {
    //    @Autowired
    @Resource
    private MusicMapper musicMapper;

    private static final Logger LOG = LoggerFactory.getLogger(MusicService.class);


    public PageResp<MusicQueryResp> list(MusicQueryReq req) {
        MusicExample musicExample = new MusicExample();
        MusicExample.Criteria criteria = musicExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Music> musicList = musicMapper.selectByExample(musicExample);

        PageInfo<Music> pageInfo = new PageInfo<>(musicList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<MusicQueryResp> respList = new ArrayList<>();

        for (Music music : musicList) {
            MusicQueryResp musicQueryResp = new MusicQueryResp();
            BeanUtils.copyProperties(music, musicQueryResp);
            respList.add(musicQueryResp);
        }

        PageResp<MusicQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);


        return pageResp;
    }

    public List<MusicQueryResp> all(MusicQueryReq req) {
        MusicExample musicExample = new MusicExample();
        MusicExample.Criteria criteria = musicExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Music> musicList = musicMapper.selectByExample(musicExample);

        List<MusicQueryResp> respList = new ArrayList<>();

        for (Music music : musicList) {
            MusicQueryResp musicQueryResp = new MusicQueryResp();
            BeanUtils.copyProperties(music, musicQueryResp);
            respList.add(musicQueryResp);
        }
        return respList;
    }

    public void save(MusicSaveReq req) {
        Music music = new Music();
        BeanUtils.copyProperties(req, music);
        if (ObjectUtils.isEmpty(req.getId())) {
            musicMapper.insert(music);
        } else {
            musicMapper.updateByPrimaryKey(music);
        }
    }
}


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
import top.nzhz.wiki.req.MusicReq;
import top.nzhz.wiki.resp.MusicResp;
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


    public PageResp<MusicResp> list(MusicReq req) {
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

        List<MusicResp> respList = new ArrayList<>();

        for (Music music : musicList) {
            MusicResp musicResp = new MusicResp();
            BeanUtils.copyProperties(music, musicResp);
            respList.add(musicResp);
        }

        PageResp<MusicResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);


        return pageResp;
    }

    public List<MusicResp> all(MusicReq req) {
        MusicExample musicExample = new MusicExample();
        MusicExample.Criteria criteria = musicExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Music> musicList = musicMapper.selectByExample(musicExample);

        List<MusicResp> respList = new ArrayList<>();

        for (Music music : musicList) {
            MusicResp musicResp = new MusicResp();
            BeanUtils.copyProperties(music, musicResp);
            respList.add(musicResp);
        }
        return respList;
    }
}


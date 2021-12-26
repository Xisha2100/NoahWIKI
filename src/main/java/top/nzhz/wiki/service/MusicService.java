package top.nzhz.wiki.service;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.nzhz.wiki.domain.Music;
import top.nzhz.wiki.domain.MusicExample;
import top.nzhz.wiki.mapper.MusicMapper;
import top.nzhz.wiki.req.MusicReq;
import top.nzhz.wiki.resp.MusicResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {

    @Resource
//    @Autowired
    private MusicMapper musicMapper;

    public List<MusicResp> list(MusicReq req) {
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


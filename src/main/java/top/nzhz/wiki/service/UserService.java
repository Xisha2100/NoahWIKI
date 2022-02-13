package top.nzhz.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.nzhz.wiki.domain.User;
import top.nzhz.wiki.domain.UserExample;
import top.nzhz.wiki.mapper.UserMapper;
import top.nzhz.wiki.req.UserQueryReq;
import top.nzhz.wiki.req.UserSaveReq;
import top.nzhz.wiki.resp.PageResp;
import top.nzhz.wiki.resp.UserQueryResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    //    @Autowired
    @Resource
    private UserMapper userMapper;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);


    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameLike("%" + req.getLoginName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<UserQueryResp> respList = new ArrayList<>();

        for (User user : userList) {
            UserQueryResp userQueryResp = new UserQueryResp();
            BeanUtils.copyProperties(user, userQueryResp);
            respList.add(userQueryResp);
        }

        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);


        return pageResp;
    }

    public List<UserQueryResp> all(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameLike("%" + req.getLoginName() + "%");
        }
        List<User> userList = userMapper.selectByExample(userExample);

        List<UserQueryResp> respList = new ArrayList<>();

        for (User user : userList) {
            UserQueryResp userQueryResp = new UserQueryResp();
            BeanUtils.copyProperties(user, userQueryResp);
            respList.add(userQueryResp);
        }
        return respList;
    }

    public void save(UserSaveReq req) {
        User user = new User();
        BeanUtils.copyProperties(req, user);
        if (ObjectUtils.isEmpty(req.getId())) {
            userMapper.insert(user);
        } else {
            userMapper.updateByPrimaryKey(user);
        }
    }


    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
    }
}


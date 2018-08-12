package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 *
 * 注册服务
 */
@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public TaotaoResult checkData(String param, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        // 1,2,3分别代表username,phone,email
        if (1 == type) {
            criteria.andUsernameEqualTo(param);
        }else if(2 == type) {
            criteria.andPhoneEqualTo(param);
        }else if (3 == type) {
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }

    /**
     * 注册服务
     * @param user
     * @return
     */
    @Override
    public TaotaoResult register(TbUser user) {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return TaotaoResult.build(400, "用户名和密码不能为空");
        }
        TaotaoResult result = checkData(user.getUsername(), 1);
        if (!(boolean)result.getData()) {
            return TaotaoResult.build(400, "用户名已存在");
        }
        if(user.getPhone() != null) {
            TaotaoResult taotaoResult = checkData(user.getPhone(), 2);
            if(!(boolean)taotaoResult.getData()) {
                return TaotaoResult.build(400, "手机号码已存在");
            }
        }
        if(user.getEmail() != null) {
            TaotaoResult taotaoResult = checkData(user.getEmail(), 3);
            if (!(boolean)taotaoResult.getData()) {
                return TaotaoResult.build(400, "Email已存在");
            }
        }
        //密码进行md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setCreated(new Date());
        user.setUpdated(new Date());
        tbUserMapper.insert(user);
        return TaotaoResult.ok();
    }
}

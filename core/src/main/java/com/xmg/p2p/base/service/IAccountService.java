package com.xmg.p2p.base.service;
import com.xmg.p2p.base.domain.Account;

public interface IAccountService {
    int insert(Account record);
    Account selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Account record);
    /**
     * 获取当前用户id的方法
     * @return
     */
    Account getCurrent();
}

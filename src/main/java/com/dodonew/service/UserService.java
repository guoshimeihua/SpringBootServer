package com.dodonew.service;

import com.dodonew.domain.User;

import java.util.List;

/**
 * Created by Bruce on 2017/11/1.
 */
public interface UserService {
    public List<User> getUserList(Integer pageIndex, Integer pageSize);
    
    public User getUser(Integer id);
    
    public boolean addUser(User user);
    
    public boolean removeUser(Integer id);
    
    public boolean modifyUser(User user);
}

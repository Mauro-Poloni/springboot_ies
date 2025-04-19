package users_ies_project.dto;

import java.util.List;

import users_ies_project.info.UserInfoDTO;

public class InfoCreateEditGeneric<T> {
    private T entity;
    private List<UserInfoDTO> users;

    public T getEntity() { return entity; }
    public void setEntity(T entity) { this.entity = entity; }
    
    public List<UserInfoDTO> getUsers() { return users; }

    public void setUsers(List<UserInfoDTO> users) { this.users = users; }
}
package users_ies_project.dto;

import java.util.List;

import users_ies_project.info.FormInfoDTO;
import users_ies_project.info.UserInfoDTO;

public class InfoCreateEditSecurityModuleUser<T> {
    private T entity;
    private List<UserInfoDTO> users;
    private List<FormInfoDTO> forms;

    public T getEntity() { return entity; }
    public void setEntity(T entity) { this.entity = entity; }
    
    public List<UserInfoDTO> getUsers() { return users; }
    public List<FormInfoDTO> getForms() { return forms; }

    public void setUsers(List<UserInfoDTO> users) { this.users = users; }
    public void setForms(List<FormInfoDTO> forms) { this.forms = forms; }
}
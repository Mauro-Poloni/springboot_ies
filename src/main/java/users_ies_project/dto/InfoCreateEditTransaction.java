package users_ies_project.dto;

import java.util.List;

import users_ies_project.info.FormInfoDTO;
import users_ies_project.info.ModuleInfoDTO;
import users_ies_project.info.UserInfoDTO;

public class InfoCreateEditTransaction<T> {
    private T entity;
    private List<UserInfoDTO> users;
    private List<FormInfoDTO> forms;
    private List<ModuleInfoDTO> modules;

    public T getEntity() { return entity; }
    public void setEntity(T entity) { this.entity = entity; }
    
    public List<UserInfoDTO> getUsers() { return users; }
    public List<FormInfoDTO> getForms() { return forms; }
    public List<ModuleInfoDTO> getModules() { return modules; }

    public void setUsers(List<UserInfoDTO> users) { this.users = users; }
    public void setForms(List<FormInfoDTO> forms) { this.forms = forms; }
    public void setModules(List<ModuleInfoDTO> modules) { this.modules = modules; }
}
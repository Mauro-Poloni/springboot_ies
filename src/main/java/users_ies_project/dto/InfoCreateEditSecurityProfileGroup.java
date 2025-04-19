package users_ies_project.dto;

import java.util.List;
import users_ies_project.info.ModuleInfoDTO;
import users_ies_project.info.ProfileInfoDTO;
import users_ies_project.info.UserInfoDTO;

public class InfoCreateEditSecurityProfileGroup<T> {
    private T entity;
    private List<UserInfoDTO> users;
    private List<ModuleInfoDTO> modules;
    private List<ProfileInfoDTO> profiles;

    public T getEntity() { return entity; }
    public void setEntity(T entity) { this.entity = entity; }
    
    public List<UserInfoDTO> getUsers() { return users; }
    public List<ModuleInfoDTO> getModules() { return modules; }
    public List<ProfileInfoDTO> getProfiles() { return profiles; }

    public void setUsers(List<UserInfoDTO> users) { this.users = users; }
    public void setModules(List<ModuleInfoDTO> modules) { this.modules = modules; }
    public void setProfiles(List<ProfileInfoDTO> profiles) { this.profiles = profiles; }
}
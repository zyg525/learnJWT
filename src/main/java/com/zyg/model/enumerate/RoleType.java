package com.zyg.model.enumerate;

public enum RoleType {

    ADMIN("admin"), USER("user");

    private String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }
}

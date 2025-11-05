package com.campus.event.constant;

public enum RoleEnum {

    ADMIN((long) 1, "ADMIN"),
    STUDENT((long) 2, "STUDENT"),
    FACULTY((long) 3, "FACULTY");

    private final Long roleId;
    private final String role;

    RoleEnum(Long id, String role) {
        this.roleId = id;
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRole() {
        return role;
    }

    // Return enum instance by roleId
    public static RoleEnum fromRoleId(Long roleId) {
        for (RoleEnum r : values()) {
            if (r.roleId == roleId) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid roleId: " + roleId);
    }

    // Return enum instance by role string (case-insensitive)
    public static RoleEnum fromRole(String role) {
        for (RoleEnum r : values()) {
            if (r.role.equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }
}

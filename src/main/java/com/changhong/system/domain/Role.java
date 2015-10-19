package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import org.springframework.security.core.GrantedAuthority;

/**
 * User: Jack Wang
 * Date: 14-7-14
 * Time: 上午10:27
 */
public class Role extends EntityBase implements GrantedAuthority {

    private RoleType roleType;

    protected Role() {
    }

    public Role(String role) {
        this.roleType = RoleType.valueOf(role);
    }

    public String getAuthority() {
        return roleType.name();
    }
}

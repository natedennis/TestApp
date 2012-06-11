package org.monarchnc.security.security;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;
import org.monarchnc.security.annotations.*;

/**
 * @author nathan dennis
 */
public class Restrictions {
    public
    @Secures
    @Admin
    boolean isAdmin(Identity identity) {
        return identity.hasRole("admin", "USERS", "GROUP");
    }
    
    public
    @Secures
    @Clinician
    boolean isClinician(Identity identity) {
        return identity.hasRole("clinician", "USERS", "GROUP");
    }

    public
    @Secures
    @EhrUser
    boolean isEhrUser(Identity identity) { 
    	boolean result = false;
    	if(identity.hasRole("BH-CL-EHR-USER", "USERS", "GROUP") || identity.hasRole("GH-CL-EHR-USER", "USERS", "GROUP")){
    		result = true;
    	}	
        return result;
    }
    
    public
    @Secures
    @Foo(bar = "abc")
    boolean isFooAbc() {
        return true;
    }

    public
    @Secures
    @Foo(bar = "def")
    boolean isFooDef() {
        return false;
    }

    public
    @Secures
    @User
    boolean isUser(Identity identity) {
        return identity.inGroup("USERS", "GROUP");
    }
    
    public @Secures @Foo(bar = "demo") boolean isDemoUser(Identity identity) {
        return identity.hasPermission("foo", "execute");
    }
    
    public @Secures @Foo(bar = "user") boolean isInUserGroup(Identity identity) {
        return identity.hasPermission("bar", "execute");
    }
    
}

package org.monarchnc.security;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.annotations.LoggedIn;
import org.monarchnc.security.annotations.*;


/**
 * @author nathan dennis
 */
public
@Model
class PrivilegedActions {
    @Inject Messages messages;

    @Admin
    public void doSomethingRestricted() {
        messages.info("doSomethingRestricted() invoked");
    }
    
    @EhrUser
    public void doEhrUserRestricted(){
    	messages.info("do something EHRUSER restricited() invoked");
    }

    @Foo(bar = "abc", zzz = "nonbindingvalue")
    public void doFooAbc() {
        messages.info("doFooAbc() invoked");
    }

    @Foo(bar = "def")
    public void doFooDef() {
        messages.info("doFooDef() invoked");
    }

    @LoggedIn
    public void doLoggedIn() {
        messages.info("doLoggedIn() invoked");
    }

    @User
    public void doUserAction() {
        messages.info("doUserAction() invoked");
    }
    
    @Foo(bar = "demo")
    public void doDemoUserRuleAction() {
        messages.info("doDemoUserRuleAction() invoked");
    }
    
    @Foo(bar = "user")
    public void doInUserGroupRuleAction() {
        messages.info("doInUserGroupRuleAction() invoked");
    }
}

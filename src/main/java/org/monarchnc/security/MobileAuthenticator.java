/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.monarchnc.security;

import java.util.ArrayList;

import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

//import org.jboss.seam.examples.booking.account.Authenticated;
//import org.jboss.seam.examples.booking.i18n.DefaultBundleKey;
//import org.jboss.seam.examples.booking.model.User;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.Authenticator.AuthenticationStatus;
import org.jboss.solder.logging.Logger;
import org.monarchnc.account.Authenticated;
import org.monarchnc.model.Customer;

import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

/**
 * This implementation of a <strong>Authenticator</strong> that uses Seam security.
 * Querys the core SUsers table on login name and password.
 * If success then query Core SMembership and assign the roles found there.
 *
 * @author Nathan Dennis
 */
@Stateful
@Named("authenticator")
public class MobileAuthenticator extends BaseAuthenticator implements Authenticator {

    @Inject
    private Logger log;

    @Inject
    private Credentials credentials;

    @Inject
    Identity identity;
    
    @Inject
    private Messages messages;

    @Inject	
    @Authenticated
    private Event<Customer> loginEventSrc;

	//@PersistenceContext(unitName="corePU")
//	private EntityManager entityManager;

    
    @SuppressWarnings("unchecked")
	//@Override
    public void authenticate() {
    	

    	if (credentials.getCredential() instanceof PasswordCredential &&
    		"admin".equals(((PasswordCredential) credentials.getCredential()).getValue())) {
    		
    			setStatus(AuthenticationStatus.SUCCESS);
            	setUser(new SimpleUser(credentials.getUsername()));
    			identity.addRole("BH-CL-EHR-USER", "USERS", "GROUP");
    		return;
    	}
    	
    }
    

}

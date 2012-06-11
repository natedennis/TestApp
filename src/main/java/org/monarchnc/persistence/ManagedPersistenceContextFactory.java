package org.monarchnc.persistence;


import org.jboss.solder.core.ExtensionManaged;
import javax.enterprise.inject.Produces;
import javax.persistence.PersistenceUnit;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManagerFactory;

public class ManagedPersistenceContextFactory {

	@Forge
	@ConversationScoped
	@PersistenceUnit(unitName="forge-default")
	@Produces
	@ExtensionManaged
	private EntityManagerFactory producerField; 
	
}
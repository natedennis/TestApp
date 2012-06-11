package org.monarchnc.view.Core;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.monarchnc.model.Customer;


/**
 * @author nathan dennis
 * IT Department
 * Monarch
 * 
 * 032012
 */

@Named("customerBean")
@Stateful
@SessionScoped
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Customer entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Customer customer;

	public Customer getCustomer() {
		return this.customer;
	}
	public void setCustomer(Customer customer){
		this.customer = customer;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName="forge-default", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;


	
	public String create() {

		this.conversation.begin();
		
		return "create?faces-redirect=true";
	}
	
	
	public void retrieve() {

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}

		//if (this.id == null) {
			//this.customer = this.search;
		//} else {
			this.customer = this.entityManager.find(Customer.class, getId());
		//}
	}
	public void retrieveId(Long id) {

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}

		if (id == null) {
			//this.customer = this.search;
		} else {
			this.customer = this.entityManager.find(Customer.class, id);
		}
	}
	
	

	/*
	 * Support updating and deleting Customer entities
	 */

	public String update() {
		this.conversation.end();
		
		try {
			if (this.id == null) {
			//	this.entityManager.persist(this.customer);
				return "search?faces-redirect=true";			
			} else {
			//	this.entityManager.merge(this.customer);
				return "view?faces-redirect=true&id=" + this.customer.getCustomerId();
			}
		} catch( Exception e ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( e.getMessage() ));
			return null;
		}
	}

	public String delete() {
		this.conversation.end();

		try {
			/*this.entityManager.remove(this.entityManager.find(Customer.class,
					getId()));
			this.entityManager.flush();*/
			return "search?faces-redirect=true";
		} catch( Exception e ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( e.getMessage() ));
			return null;
		}
	}

	/*
	 * Support searching Customer entities with pagination
	 */

	private int page;
	private long count;
	private List<Customer> pageItems = new ArrayList<Customer>();
	
	private Customer search = new Customer();

	



	public int getPageSize() {
		return 10;
	}

	public Customer getSearch() {
		return this.search;
	}

	public void setSearch(Customer search) {
		this.search = search;
	}

	public void search() {
		this.page = 0;
		updateResults(this.maxResults, 1);
	}

	public void paginate() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Customer> root = countCriteria.from(Customer.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
		root = criteria.from(Customer.class);
		TypedQuery<Customer> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	public void paginatemobile() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Customer> root = countCriteria.from(Customer.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
		root = criteria.from(Customer.class);
		TypedQuery<Customer> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				250);
		this.pageItems = query.getResultList();
	}
	
	
	private Predicate[] getSearchPredicates(Root<Customer> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String firstName = this.search.getFirstName();
		if (firstName != null && !"".equals(firstName)) {
			predicatesList.add(builder.like(root.<String>get("firstName"), '%' + firstName + '%'));
		}
		String 	lastName = this.search.getLastName();
		if (lastName != null && !"".equals(lastName)) {
			predicatesList.add(builder.like(root.<String>get("lastName"), '%' + lastName + '%'));
		}
		
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Customer> getPageItems() {

		return this.pageItems;
	}
	
	
	
	
	
	public long getCount() {
		return this.count;
	}
	
	private int maxResults=4;
	private int resultCount=0;
	private int firstResult = 0;
	private String paging;
	
	

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public String getPaging() {
		return paging;
	}

	public void setPaging(String paging) {
		this.paging = paging;
	}

	public List<Customer> getPageItemsMobile() {
		
		return this.pageItems;
	}
	
	
	//@PostConstruct
	public void init() {
		System.out.println("IN POSTCONTRUCT");
		if (FacesContext.getCurrentInstance().isPostback()) {
			System.out.println("IN postback");
			return;
		}


		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}

		setPage(0);
	}
	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		System.out.println("page: " + page);
		if(page == 0 ){
			updateResults(this.maxResults, 1);
		//} else if(page == 1 ){
		//	updateResults(this.maxResults, this.maxResults+1);
		} else {
			updateResults(this.maxResults, (((page-1)*this.maxResults)+1));
		}
		this.page = page;
	}	
	
	private void updateResults(int maxResults, int firstResult) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Customer> root = countCriteria.from(Customer.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
		root = criteria.from(Customer.class);
		TypedQuery<Customer> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
	
		
	    this.resultCount = query.getResultList().size();
	    
	    List<Customer> temp = new ArrayList<Customer>();
	    
	    temp = query
	        .setMaxResults(maxResults)
	        .setFirstResult(firstResult)
	        .getResultList();
	    
	    //ajax asshatery
	    this.pageItems.clear();
	    if(temp.size() < this.count){
	    	//page 1 we dont want this because we have to use the set page method to
	    	//to execute the query.. it wont be called on the first page
	    	//use postcontruct
	    	
	    		Customer s = new Customer();
				for(int i = 1; i < firstResult;i++){
					this.pageItems.add(s);
				}
				System.out.println("firstResult: " + firstResult);
				for(int i = 0; i < temp.size(); i++){
					System.out.println("name: " + temp.get(i).getFirstName() + " " + temp.get(i).getLastName()); 
				} 
				this.pageItems.addAll(temp);
				for(int i =0; i < this.count-(firstResult+temp.size());i++){
					this.pageItems.add(s);
				}
				System.out.println("remainder: " +  (this.count-(firstResult+temp.size())));
				System.out.println("count: " + count);
	    	
		} else {
			this.pageItems = temp;
		}
				    

	}
	
	/*
	 * Support listing and POSTing back Customer entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Customer> getAll() {

		CriteriaQuery<Customer> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Customer.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Customer.class))).getResultList();
	}

	public Converter getConverter() {

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return CustomerBean.this.entityManager.find(Customer.class,
						Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((Customer) value).getCustomerId());
			}
		};
	}
	
	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */
	private Customer add = new Customer();

	public Customer getAdd() {
		return this.add;
	}

	public Customer getAdded() {
		Customer added = this.add;
		this.add = new Customer();
		return added;
	}
}
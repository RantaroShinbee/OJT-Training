package com.customers.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.customers.entity.Customer;
import com.mongodb.client.result.UpdateResult;

@Repository
public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public int update(Customer customer) {
		Query customer_ = new Query(Criteria.where("_id").is(customer.get_id()));
		Update update = new Update();
		update.set("name", customer.getName());
		update.set("updateAt", new java.util.Date());
		UpdateResult result = this.mongoTemplate.updateFirst(customer_, update, Customer.class);
		if (result != null) {
			return (int) result.getModifiedCount();
		}
		return 0;
	}

}

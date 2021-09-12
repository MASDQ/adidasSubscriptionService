package com.adidas.adidas.repository;

import com.adidas.adidas.dto.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for subscription operations
 */

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    Subscription findOneById(Integer Id);
    Subscription findAllByEmail(String Email); //TODO eliminar
}

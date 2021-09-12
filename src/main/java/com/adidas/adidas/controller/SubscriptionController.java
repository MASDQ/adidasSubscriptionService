package com.adidas.adidas.controller;

import com.adidas.adidas.common.CommonUtils;
import com.adidas.adidas.exception.FailedOperationException;
import com.adidas.adidas.models.GenericResponse;
import com.adidas.adidas.repository.SubscriptionRepository;
import com.adidas.adidas.dto.Subscription;
import com.adidas.adidas.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

/**
 * Controller class, cointains all the operations for the API
 * It's showed on the "/subscriptions" route, followed of the specific path of each method
 * Example:
 * /subscriptions/create for create a subscription
 * /subscriptions/delete for deleting an existing one
 * /subscriptions/recover for recover one subcription or a list
 *
 */
@RestController
@RequestMapping(path="/subscription", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    final static Logger log = Logger.getLogger(SubscriptionController.class);

    /**
     * Method for subscription creation, expect a subscription, make the process of creation and
     * if there are no errors, returns the Subscription Id, otherwise return an exception
     * On a successfull creation, it also verify if the email must be sended and, if so, send it
     * it MUST receibe a Subscription, but there is a problem and it receibe instead LinkedHashMap
     *
     * @param subscription
     * @return a ResponseEntity object with whe Subscription Id
     * @throws FailedOperationException
     */
    @PutMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSubscription(@RequestBody Object subscription) throws FailedOperationException {
        log.debug("create subscription begin");
        Subscription subscriptionToCreate = (Subscription) ((LinkedHashMap) subscription).get("Subscription");

        try {
            subscriptionCreation(subscriptionToCreate);
            if (subscriptionToCreate.getId() != null) {

                if (subscriptionToCreate.getConsent() && CommonUtils.validString(subscriptionToCreate.getEmail())) {
                    sendEmail(subscriptionToCreate.getEmail());
                }

                return new ResponseEntity<>(CommonUtils.successfullResponse(
                        Constants.Response.RETURN_SUBSCRIPTION_CREATION_OK + subscriptionToCreate.getId()),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(CommonUtils.failureResponse(
                        Constants.Response.RETURN_SUBSCRIPTION_CREATION_OK), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("create subscription exception");
            throw new FailedOperationException(e.getMessage());
        }
    }

    /**
     * MEthod to delete a subscription, it expect an Id and try to delete if there is any subscription Id which match
     *
     * @param id
     * @return a ResponseEntity with a message for the user
     * @throws FailedOperationException
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteSubscription(@PathVariable String id) throws FailedOperationException {
        log.debug("delete subscription bagin");
        try {
            boolean result = false;
            String response = "";
            if (CommonUtils.validString(id)) {
                result = subscriptionDeletion(id);
            }
            if (result) {
                response = Constants.Response.RETURN_SUBSCRIPTION_DELETION_PREVIOUS + id +
                        Constants.Response.RETURN_SUBSCRIPTION_DELETION_SUCCESS;
            } else {
                response = Constants.Response.RETURN_SUBSCRIPTION_DELETION_PREVIOUS + id +
                        Constants.Response.RETURN_SUBSCRIPTION_DELETION_FAILURE;
            }
            log.debug("delete subscription end");
            return new ResponseEntity<>(CommonUtils.successfullResponse(response),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error("delete subscription exception");
            throw new FailedOperationException(e.getMessage());
        }
    }

    /**
     * Method to recover a Single subscriptor based on a subscription id
     *
     * @param id
     * @return ResponseEntity with a Subscriptor if is exist
     * @throws FailedOperationException
     */
    @GetMapping("/recover/single/{id}")
    public ResponseEntity<Object> recoverSingleSubscription(@PathVariable String id) throws FailedOperationException {
        try {
            log.debug("recover single subscription start");
            Subscription subscriptionResponse = null;
            GenericResponse response = null;

            if (CommonUtils.validString(id)) {
                subscriptionResponse = singleSubscriptionSearch(id);
            }
            if (subscriptionResponse != null) {
                log.debug("recover single subscription - subscription finded");
                response = CommonUtils.successfullResponse(subscriptionResponse);
            } else {
                log.debug("recover single subscription - subscription nof found");
                response = CommonUtils.failureResponse(Constants.Response.RETURN_SUBSCRIPTION_SEARCH_FAILURE);
            }
            log.debug("recover single subscription finish");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("recover single subscription exception");
            throw new FailedOperationException(e.getMessage());
        }
    }

    /**
     * Method to recover subscriptors based on one or more subscriptions ids
     * it MUST receibe a list of Subscriptions IDs, but there is a problem and it receive instead LinkedHashMap
     *
     * @param subscriptionsIDs
     * @return ResponseEntity with as many Subscriptors as is exists
     * @throws FailedOperationException
     */
    @SneakyThrows
    @GetMapping("/recover/all")
    public ResponseEntity<Object> recoverManySubscriptions(@RequestBody Object subscriptionsIDs) {
        try {
            log.debug("recover subscription start");

            ArrayList<String> listIds = (ArrayList<String>) ((LinkedHashMap) subscriptionsIDs).get("SubscriptionsIDs");

            if (CommonUtils.validList(listIds)) {
                ArrayList<Integer> listIntegerIds = new ArrayList<>();

                for (String id : listIds) {
                    listIntegerIds.add(Integer.parseInt(id));
                }

                ArrayList<Subscription> subscriptionList = subscriptionsSearch(listIntegerIds);

                if (CommonUtils.validList(subscriptionList)) {
                    log.debug("recover subscription finish");
                    return new ResponseEntity<>(CommonUtils.successfullResponse(subscriptionList), HttpStatus.OK);

                } else {
                    log.debug("recover subscription finish");
                    return new ResponseEntity<>(CommonUtils.failureResponse(
                            Constants.Response.RETURN_SUBSCRIPTION_SEARCH_MANY_FAILURE), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(CommonUtils.failureResponse(
                        Constants.Response.RETURN_SUBSCRIPTION_SEARCH_MANY_FAILURE), HttpStatus.BAD_REQUEST);
            }
        } catch (ClassCastException e) {
            log.error("recover many subscription exception");
            throw new FailedOperationException(e.getMessage());
        } catch (Exception e) {
            log.error("recover many subscription exception");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method with the logic to create a subscription and return the ID
     *
     * @param subscription subscription to create
     * @return ID of the subscruption after creation
     */
    private void subscriptionCreation(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    /**
     * Method with the logic to delete a subscription and return a proper message according to the result
     *
     * @param id the id of the Subscription to delete
     * @return boolean, true if the subscription was deleted, otherwise return false
     */
    public boolean subscriptionDeletion(String id) {
        subscriptionRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    /**
     * Methot to search an recover only one subscripbion based on a ID
     *
     * @param id the ID to search
     * @return a Subscription founded, if there are none, return null
     */
    public Subscription singleSubscriptionSearch(String id) {
        Subscription subscriptionRecovered = subscriptionRepository.findOneById(Integer.parseInt(id));
        if (subscriptionRecovered != null) {
            return subscriptionRecovered;
        } else {
            return null;
        }

    }

    /**
     * Method to search many Subscription based on their IDs
     *
     * @param ids list of subscriptions IDs
     * @return list of subscriptions recovered
     */
    public ArrayList<Subscription> subscriptionsSearch(ArrayList<Integer> ids) {
        return (ArrayList<Subscription>) subscriptionRepository.findAllById(ids);
    }

    /**
     * Method to send an email
     *
     * @param email the receiver email
     */
    public void sendEmail(String email) {
        log.debug("begining mail send");
        final String uri = Constants.MAIL_URI + email;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        log.debug("finish mail send");
    }
}

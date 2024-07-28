package com.thery.paymybuddy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.thery.paymybuddy.exception.RelationShipsServiceException.*;
import static com.thery.paymybuddy.exception.RelationShipsServiceException.RelationshipsAlreadyExistException.*;
import static com.thery.paymybuddy.constant.MessageExceptionConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelationShipsControllerAdviceTest {

    private final RelationShipsControllerAdvice advice = new RelationShipsControllerAdvice();

    @Test
    public void testHandleAddRelationShipsException() {
        AddRelationShipsException ex = new AddRelationShipsException(new RuntimeException());

        ResponseEntity<String> response = advice.handleAddRelationShipsException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ADD_RELATIONSHIPS_EXCEPTION, response.getBody());
    }

    @Test
    public void testHandleAddRelationShips_RelationshipsAlreadyExistException() {
        AddRelationShipsException ex = new AddRelationShipsException(new RelationshipsAlreadyExistException());

        ResponseEntity<String> response = advice.handleAddRelationShipsException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(RELATIONSHIPS_ALREADY_EXIST_EXCEPTION, response.getBody());
    }

    @Test
    public void testHandleAddRelationShips_SelfOrientedRelationshipException() {
        AddRelationShipsException ex = new AddRelationShipsException(new SelfOrientedRelationshipException());

        ResponseEntity<String> response = advice.handleAddRelationShipsException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(SELF_ORIENTED_RELATIONSHIP_EXCEPTION, response.getBody());
    }


    @Test
    public void testHandleRelationShipsDetailForTransferException() {
        RelationShipsDetailForTransferException ex = new RelationShipsDetailForTransferException(new RuntimeException());

        ResponseEntity<String> response = advice.handleRelationShipsDetailForTransferException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(RELATIONSHIPS_DETAIL_FOR_TRANSFER_EXCEPTION, response.getBody());
    }
}

package com.riwi.librosYa.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.librosYa.api.dto.DTOReservation;
import com.riwi.librosYa.infraestructure.abstract_Services.IReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/reservations")
@Tag(name = "Reservations")
public class ReservationController {

    @Autowired
    private final IReservationService reservationService;

    @Operation(summary = "Create a new reservation", description = "Send information to create a new reservation")
    @PostMapping
    public ResponseEntity<DTOReservation> save(@Validated @RequestBody DTOReservation reservation) {
        return ResponseEntity.ok(this.reservationService.create(reservation));
    }

    @Operation(summary = "Delete a reservation by id", description = "Send the reservation id to delete it")
    @DeleteMapping(path = "/{reservation_id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a reservation", description = "Send information to update a reservation")
    @PutMapping(path = "/{reservation_id}")
    public ResponseEntity<DTOReservation> update(@PathVariable Long id, @Validated @RequestBody DTOReservation reservation) {
        return ResponseEntity.ok(this.reservationService.update(reservation, id));
    }

    @Operation(summary = "Get all reservations of a user", description = "Get all reservations of a user by user id with pagination")
    @GetMapping("/users/{user_id}/reservations")
    public ResponseEntity<Page<DTOReservation>> getAllReservationsByUser(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return ResponseEntity.ok(this.reservationService.getReservationsByUserId(userId, pageable));
    }

    @Operation(summary = "Get all reservations of a book", description = "Get all reservations of a book by book id with pagination")
    @GetMapping("/books/{book_id}/reservations")
    public ResponseEntity<Page<DTOReservation>> getAllReservationsByBook(
            @PathVariable Long bookId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return ResponseEntity.ok(this.reservationService.getReservationsByBookId(bookId, pageable));
    }

    @Operation(summary = "Get a reservation by id", description = "Send the reservation id to get reservation details")
    @GetMapping("/{reservation_id}")
    public ResponseEntity<DTOReservation> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.reservationService.get(id));
    }
}

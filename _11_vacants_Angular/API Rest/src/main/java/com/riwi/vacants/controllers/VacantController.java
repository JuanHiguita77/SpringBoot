package com.riwi.vacants.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.vacants.services.interfaces.IVacantService;
import com.riwi.vacants.utils.dto.errors.ErrorResponse;
import com.riwi.vacants.utils.dto.request.VacantRequest;
import com.riwi.vacants.utils.dto.response.VacantResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



//Rest controller es para responder con respuesta http
@RestController
@RequestMapping("/vacant")
@AllArgsConstructor
@Tag(name = "Vacants")
public class VacantController 
{
    @Autowired
    private final IVacantService iVacantService;

    @ApiResponse(
        responseCode = "400",
        description = "When the id is not valid",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        }
    )

    @Operation(
        summary = "Endpoint to list all Paginate vacants",
        description = "To use you need send a number page and size to get all vacants paginate"
    )
    @GetMapping
    public ResponseEntity<Page<VacantResponse>> listAll(
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(defaultValue = "3") int size ) 
    {
        return ResponseEntity.ok(this.iVacantService.list(page - 1, size));
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<VacantResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.iVacantService.getById(id));
    }
    
    @PostMapping("/add")
    public ResponseEntity<VacantResponse> add(@Validated @RequestBody VacantRequest vacant) {
        return ResponseEntity.ok(this.iVacantService.insert(vacant));
    }

    @DeleteMapping("/delete/{id}")
    //Map<tipo de dato de la llave, tipo de dato a devolver>
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id)
    {
        this.iVacantService.delete(id);

        //Crear el map
        Map<String, String> response = new HashMap<>();

        //Añadir los datos al map con put
        response.put("message", "Deleted Sucessfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<VacantResponse> update(@PathVariable Long id, @Validated @RequestBody VacantRequest entity) 
    {
        return ResponseEntity.ok(this.iVacantService.update(id, entity));
    }
}

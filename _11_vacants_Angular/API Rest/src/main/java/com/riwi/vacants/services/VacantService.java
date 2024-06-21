package com.riwi.vacants.services;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.vacants.entities.Company;
import com.riwi.vacants.entities.Vacant;
import com.riwi.vacants.repositories.CompanyRepository;
import com.riwi.vacants.repositories.VacantRepository;
import com.riwi.vacants.services.interfaces.IVacantService;
import com.riwi.vacants.utils.dto.request.VacantRequest;
import com.riwi.vacants.utils.dto.response.CompanyToVacantResponse;
import com.riwi.vacants.utils.dto.response.VacantResponse;
import com.riwi.vacants.utils.enums.StateVacant;
import com.riwi.vacants.utils.exceptions.idNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VacantService implements IVacantService{
    
    @Autowired
    private final VacantRepository vacantRepository;
    @Autowired
    private final CompanyRepository companyRepository;

    @Override
    public VacantResponse getById(Long id) 
    {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public void delete(Long id) 
    {   
        Vacant vacant = this.find(id); 

        this.vacantRepository.delete(vacant);   
    }

    @Override
    public VacantResponse insert(VacantRequest request) 
    {
        Company company = this.companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new idNotFoundException("Company"));

        Vacant vacant = this.requestToVacant(request, new Vacant());

        vacant.setCompany(company);

        return this.entityToResponse(this.vacantRepository.save(vacant));
    }

    @Override
    public Page<VacantResponse> list(int page, int size) {
        if(page < 0) page = 0;

        PageRequest pagination = PageRequest.of(page, size);

        return this.vacantRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public VacantResponse update(Long id, VacantRequest request) 
    {
        Vacant vacant = this.find(id);

        Company company = this.companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new idNotFoundException("Company"));

        vacant = this.requestToVacant(request, vacant);

        //Puede actualizar la compañia y el status
        vacant.setCompany(company);

        if (request.getStatus() != null) vacant.setStatus(request.getStatus()); 

        return this.entityToResponse(this.vacantRepository.save(vacant));
    }

    private VacantResponse entityToResponse(Vacant vacant)
    {
        VacantResponse response = new VacantResponse();
        CompanyToVacantResponse companyToVacantResponse = new CompanyToVacantResponse();

        BeanUtils.copyProperties(vacant, response);
        BeanUtils.copyProperties(vacant.getCompany(), companyToVacantResponse);

        //Se agrega el dto de respuesta de la compañia
        response.setCompany(companyToVacantResponse);
        
        return response;
    }

    private Vacant requestToVacant(VacantRequest request, Vacant vacant)
    {
        vacant.setDescription(request.getDescription());
        vacant.setTitle(request.getTitle());
        //Por default no se pide al usuario, se pone automatica
        vacant.setStatus(StateVacant.ACTIVE);

        return vacant;
    }

    private Vacant find(Long id)
    {
        return this.vacantRepository.findById(id).orElseThrow(() -> new idNotFoundException("Vacant"));
    }
}

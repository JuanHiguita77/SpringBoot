package com.riwi.vacants.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.vacants.entities.Company;
import com.riwi.vacants.entities.Vacant;
import com.riwi.vacants.repositories.CompanyRepository;
import com.riwi.vacants.services.interfaces.ICompanyService;
import com.riwi.vacants.utils.dto.request.CompanyRequest;
import com.riwi.vacants.utils.dto.response.CompanyResponse;
import com.riwi.vacants.utils.dto.response.VacantToCompanyResponse;
import com.riwi.vacants.utils.exceptions.idNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor//CompanyRepository es una constante entonces necesita un constructor
//El constructor es para darle el valor desde el inicio, ya que luego no se puede modificar
public class CompanyServices implements ICompanyService
{
    
    @Autowired
    private final CompanyRepository companyRepository;
    
    @Override
    public CompanyResponse getById(String id) 
    {
        return this.entityToResponse(this.find(id));
    }

    private Company find(String id)
    {
        return this.companyRepository.findById(id).orElseThrow(() -> new idNotFoundException("Company"));
    }

    @Override
    public Page<CompanyResponse> list(int page, int size) 
    {
        if (page < 0) 
        {
            page = 0;    
        }

        PageRequest pagination = PageRequest.of(page, size);

        //return this.companyRepository.findAll(pagination).map(company -> this.entityToResponse(company)); expresion lambda flecha o inferencial ::

        return this.companyRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public void delete(String id) 
    {
        Company company = this.find(id);   
        this.companyRepository.delete(company);
    }

    @Override
    public CompanyResponse insert(CompanyRequest request) 
    {
        Company company = this.requestToCompany(request, new Company());
    
        return this.entityToResponse(this.companyRepository.save(company));
    }

    @Override
    public CompanyResponse update(String id, CompanyRequest request) {
        Company company = this.find(id);
        Company companyRequest = this.requestToCompany(request, company);

        return this.entityToResponse(this.companyRepository.save(companyRequest));
    }
    
    private CompanyResponse entityToResponse(Company company)
    {
        CompanyResponse response = new CompanyResponse();

        /*response.setId(company.getId());
        response.setName(company.getName());
        response.setLocation(company.getLocation());
        response.setVacants(company.getVacants());
        response.setContact(company.getContact());*/

        //Lo reemplazamos por esto:
        BeanUtils.copyProperties(company, response);

        //Mapeamos las vacantes para convertirlas al dto de response
        //stream(): Convierte lista a coleccion para acceder a los metodos para recorrer(map, for earch...etc)
        //collect(Collectors.toList()): convierte de nuevo la coleccion a lista
        response.setVacants(company.getVacants().stream().map(this::vacantToResponse).collect(Collectors.toList()));

        return response;
    }

    private VacantToCompanyResponse vacantToResponse(Vacant vacant)
    {
        VacantToCompanyResponse response = new VacantToCompanyResponse();

        BeanUtils.copyProperties(vacant, response);

        return response;
    }

    private Company requestToCompany(CompanyRequest request, Company company)
    {
        BeanUtils.copyProperties(request, company);
        company.setVacants(new ArrayList<>());

        return company;
    }
}

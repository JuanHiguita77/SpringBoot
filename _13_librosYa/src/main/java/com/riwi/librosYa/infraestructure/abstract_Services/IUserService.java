package com.riwi.librosYa.infraestructure.abstract_Services;

import com.riwi.librosYa.api.dto.DTOUser;
import com.riwi.librosYa.infraestructure.services.CrudService;

public interface IUserService extends CrudService<DTOUser, DTOUser, Long>
{
}

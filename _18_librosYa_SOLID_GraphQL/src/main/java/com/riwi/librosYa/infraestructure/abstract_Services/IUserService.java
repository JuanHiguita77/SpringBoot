package com.riwi.librosYa.infraestructure.abstract_Services;

import com.riwi.librosYa.api.dto.DTOUser;
import com.riwi.librosYa.infraestructure.services.CreateService;
import com.riwi.librosYa.infraestructure.services.DeleteService;
import com.riwi.librosYa.infraestructure.services.GetService;
import com.riwi.librosYa.infraestructure.services.UpdateService;

public interface IUserService extends CreateService<DTOUser, DTOUser>, 
                                    UpdateService<DTOUser, DTOUser, Long>, 
                                    GetService<DTOUser, Long>, 
                                    DeleteService<Long>
{
}

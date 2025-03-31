package riwi.lastfilter.spring.infrastructure.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import riwi.lastfilter.spring.api.dto.request.CouponRequest;
import riwi.lastfilter.spring.domain.entities.Coupon;
import riwi.lastfilter.spring.domain.repositories.CoupontRepository;
import riwi.lastfilter.spring.infrastructure.abstrasct_services.ICreate;
import riwi.lastfilter.spring.infrastructure.abstrasct_services.IDelete;
import riwi.lastfilter.spring.infrastructure.abstrasct_services.IFindAll;
import riwi.lastfilter.spring.infrastructure.abstrasct_services.IUpdate;
import riwi.lastfilter.spring.utils.mappers.CouponMapper;

@SuppressWarnings("rawtypes")
@Service
@Data
@AllArgsConstructor
public class CouponService implements ICreate,IDelete,IFindAll,IUpdate{

    @Autowired
    private final CoupontRepository couponRepository;

    @Autowired
    private final CouponMapper couponMapper;

    
    @Override
    public Object create(Object request) {
        
        Coupon coupon = this.couponMapper.toGetEntity((CouponRequest) request);
        coupon.setBuys(new ArrayList<>());

        return this.couponMapper.toGetResp(this.couponRepository.save(coupon));
    }
    @Override
    public Object findAll(Object request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Object update(Object request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Object delete(Object request) {
        return null;
    }

    
}

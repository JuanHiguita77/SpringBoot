package riwi.lastfilter.spring.infrastructure.abstrasct_services;

public interface IFindAll<Req, Res> {

    public Res findAll(Req request);

}
package riwi.lastfilter.spring.infrastructure.abstrasct_services;

public interface ICreate <Req, Res> {

    public Res create(Req request);

}
package riwi.lastfilter.spring.infrastructure.abstrasct_services;

public interface IUpdate<Req, Res> {

    public Res update(Req request);

}
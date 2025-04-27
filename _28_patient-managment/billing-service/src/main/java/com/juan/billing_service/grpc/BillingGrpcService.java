package com.juan.billing_service.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;


@GrpcService
public class BillingGrpcService extends BillingServiceImplBase
{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BillingGrpcService.class);

    @Override 
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("Received gRPC request to create billing account: {}", billingRequest);

        BillingResponse response = BillingResponse.newBuilder()
            .setAccountId("1")
            .setStatus("SUCCESS")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        log.info("gRPC response sent: {}", response);
    }
}

package com.juan.patient_managment.grpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class BillingServiceGrpcClient {
    
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    //localhost:9091/BillingService/CreatePatientAccount
    //aws.grpc:123123/BillinfService/CreatePatientAccount
    public BillingServiceGrpcClient(
    @Value("${billing.service.address:localhost}") String serverAddress,
    @Value("${bulling.service.grpc.port:9091}") int serverPort)
    {
        log.info("Connecting to billing service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
        log.info("Connected to billing service at {}:{}", serverAddress, serverPort);
    }

    public BillingResponse createBillingAccount(String patiendId, String name, String email)
    {
        BillingRequest request = BillingRequest.newBuilder()
            .setPatientId(patiendId)
            .setName(name)
            .setEmail(email)
            .build();

        log.info("Creating billing account for patient: {}", patiendId);

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Billing account created for patient: {} with account id: {}", patiendId, response.getAccountId());

        return response;

    }
}

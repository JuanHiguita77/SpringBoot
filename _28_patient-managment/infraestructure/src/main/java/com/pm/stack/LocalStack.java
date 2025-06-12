package com.pm.stack;


import software.amazon.awscdk.services.rds.Credentials;

import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.CloudMapNamespaceOptions;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerDefinitionOptions;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.FargateService;
import software.amazon.awscdk.services.ecs.FargateTaskDefinition;
import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.PortMapping;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.App;
import software.amazon.awscdk.AppProps;
import software.amazon.awscdk.BootstraplessSynthesizer;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.Token;
import software.amazon.awscdk.services.msk.CfnCluster;
import software.amazon.awscdk.services.ec2.ISubnet;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.rds.PostgresEngineVersion;
import software.amazon.awscdk.services.rds.PostgresInstanceEngineProps;
import software.amazon.awscdk.services.route53.CfnHealthCheck;

   // LocalStack class extends the AWS CDK Stack class, representing a deployable unit of your application.
public class LocalStack extends Stack 
//to test connection with localstack: mvn compile exec:java -Dexec.mainClass=com.pm.stack.LocalStack
{

    private final Vpc vpc;

    private final Cluster ecsCluster;// Placeholder for ECS Cluster

    // Constructor for LocalStack. It takes an App (the scope), a unique ID, and stack properties.
    public LocalStack(final App scope, final String id, final StackProps props) 
    {
        // Calls the parent class (Stack) constructor to initialize the stack.
        super(scope, id, props);

        this.vpc = createVpc();

        DatabaseInstance authServiceDb = createDatabase("AuthServiceDb", "auth-service-db");

        DatabaseInstance patientServiceDb = createDatabase("PatientServiceDb", "patient-service-db");

        CfnHealthCheck authDbHealthCheck = createDbHealthCheck(authServiceDb, "AuthServiceDbHealthCheck");

        CfnHealthCheck patientDbHealthCheck = createDbHealthCheck(patientServiceDb, "PatientServiceDbHealthCheck");

        CfnCluster mskCluster = createMskCluster();

        this.ecsCluster = createEcsCluster();

        FargateService authService = createFargateService(
            "AuthService", 
            "auth-service", 
            List.of(8084), 
            authServiceDb, 
            Map.of(
                "JWT_SECRET", "X6krczRMzNMW6kJf07Sk7QMXKzN0kEh+aZ+BZu9Qf3k="
        ));

        authService.getNode().addDependency(authServiceDb); // Ensure the auth service depends on the database instance.
        authService.getNode().addDependency(authDbHealthCheck); // Ensure the auth service depends on the database health check.

        FargateService billingService = createFargateService(
            "BillingService", 
            "billing-service", 
            List.of(8081, 9091), 
            null, // No database for billing service
            null);

        FargateService analyticsService = createFargateService(
            "AnalyticsService", 
            "analytics-service", 
            List.of(8082), 
            null, // No database for analytics service
            null);

        analyticsService.getNode().addDependency(mskCluster); // Ensure the analytics service depends on the MSK cluster.

        FargateService patientService = createFargateService(
            "PatientService", 
            "patient-service", 
            List.of(8080), 
            patientServiceDb, 
            Map.of(
                "BILLING_SERVICE_ADDRESS", 
                "host.docker.internal",
                "BILLING_SERVICE_GRPC_PORT",
                "9001"                     
        ));

        createApiGatewayService();

        patientService.getNode().addDependency(patientServiceDb); // Ensure the patient service depends on the database instance.
        patientService.getNode().addDependency(patientDbHealthCheck); // Ensure the patient service depends on the database health check.
        patientService.getNode().addDependency(billingService); // Ensure the patient service depends on the billing service.
        patientService.getNode().addDependency(mskCluster); // Ensure the patient service depends on the MSK cluster.
    }

    private Vpc createVpc() 
    {
        // Create a new VPC (Virtual Private Cloud) with default settings.
        // This method is a placeholder and should be implemented to create a VPC as per your requirements.
        return Vpc.Builder.create(this, "PatientManagementVPC")
                .vpcName("PatientManagementVPC") // Set the name of the VPC
                .maxAzs(2) // Set the maximum number of Availability Zones
                .build();
    }

    // Method to create a database instance.
    private DatabaseInstance createDatabase(String id, String dbName)
    {
        // This method is a placeholder for creating a database instance.
        // Create a new database instance using the DatabaseInstance.Builder.
        return DatabaseInstance.Builder
            .create(this, id) // Set the scope and unique identifier for the database instance.
            .engine(DatabaseInstanceEngine.postgres(PostgresInstanceEngineProps.builder()
                .version(PostgresEngineVersion.VER_17_2).build())) // Specify the PostgreSQL engine and version.
            .vpc(vpc) // Associate the database instance with the VPC.
            .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO)) // Set the instance type to a cost-effective option.
            .allocatedStorage(20) // Allocate 20 GB of storage for the database.
            .credentials(Credentials.fromGeneratedSecret("admin_user")) // Generate credentials for the admin user.
            .databaseName(dbName) // Set the name of the database.
            .removalPolicy(RemovalPolicy.DESTROY) // Configure the removal policy to destroy the database when the stack is deleted.
            .build(); // Build and return the database instance.           
    }

    private CfnHealthCheck createDbHealthCheck(DatabaseInstance db, String id)
    {
        return CfnHealthCheck.Builder.create(this, id)
            .healthCheckConfig(CfnHealthCheck.HealthCheckConfigProperty.builder()
            .type("TCP")
            .port(Token.asNumber(db.getDbInstanceEndpointPort()))
            .ipAddress(db.getDbInstanceEndpointAddress())
            .requestInterval(30)
            .failureThreshold(3)
            .build())       
        .build();
    }

    // Method to create an MSK (Managed Streaming for Kafka) cluster.
    private CfnCluster createMskCluster() 
    {
        // Create a new MSK cluster using the CfnCluster.Builder.
        return CfnCluster.Builder.create(this, "MskCluster")
            .clusterName("Kafka-cluster") // Set the name of the Kafka cluster.
            .kafkaVersion("2.8.0") // Specify the Kafka version (recommended version).
            .numberOfBrokerNodes(2) // Set the number of broker nodes in the cluster.
            .brokerNodeGroupInfo(CfnCluster.BrokerNodeGroupInfoProperty.builder()
                .instanceType("kafka.m5.xlarge") // Specify the instance type for the broker nodes.
                .clientSubnets(vpc.getPrivateSubnets().stream()
                                .map(ISubnet::getSubnetId) // Map the private subnets to their subnet IDs.
                                .collect(Collectors.toList())) // Collect the subnet IDs into a list.
                .brokerAzDistribution("DEFAULT") // Set the availability zone distribution to default.
                .build()) // Build the BrokerNodeGroupInfo property.
            .build(); // Build and return the MSK cluster.
    }

    private Cluster createEcsCluster() 
    {
        return Cluster.Builder.create(this, "PatientManagementEcsCluster")
            .vpc(vpc) // Associate the ECS cluster with the VPC.
            .defaultCloudMapNamespace(CloudMapNamespaceOptions.builder()
                .name("patient-managment.local")
                .build())
            .build(); // Build and return the ECS cluster.
    }

    //Ecs service creation method.
    // This method is a placeholder and should be implemented to create an ECS Fargate service.
    private FargateService createFargateService(String id, String imageName, List<Integer> ports, DatabaseInstance db, Map<String, String> additionalEnvVars)
    {
        FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder
            .create(this, id + "Task")
            .cpu(256) // Set the CPU units for the task.
            .memoryLimitMiB(512) // Set the memory limit for the task in MiB.
            .build();

        ContainerDefinitionOptions.Builder containerOptions = ContainerDefinitionOptions.builder()
            .image(ContainerImage.fromRegistry(imageName))
            .portMappings(ports.stream()
            .map(port -> PortMapping.builder()
                .containerPort(port) // Specify the container port to be mapped.
                .hostPort(port) // Map the container port to the host port.
                .protocol(software.amazon.awscdk.services.ecs.Protocol.TCP) // Set the protocol to TCP.
                .build())
                .toList()) // Map the container ports to the specified ports.
            .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
            .logGroup(LogGroup.Builder.create(this, id + "LogGroup")
                .logGroupName("/ecs/" + id) // Set the log group name for the ECS service.
                .removalPolicy(RemovalPolicy.DESTROY) // Set the removal policy to destroy the log group when the stack is deleted.
                .retention(RetentionDays.ONE_DAY) // Set the log retention period to one day.
                .build())
            .streamPrefix(imageName) // Set the log stream prefix to the image name.
            .build())); // Configure the logging driver to use AWS CloudWatch Logs.

            Map<String, String> envVars = new HashMap<>();
            envVars.put("SPRING_KAFKA_BOOTSTRAP_SERVERS", "localhost.localstack.cloud:4511, localhost.localstack.cloud:4512");

            if(additionalEnvVars != null) 
            {
                envVars.putAll(additionalEnvVars); // Add any additional environment variables provided.
            }

            // If a database instance is provided, set the database connection URL in the environment variables.
            if(db != null) 
            {
                envVars.put("SPRING_DATASOURCE_URL", "jdbc:postgresql://%s:%s/%s-db"
                    .formatted(
                        db.getDbInstanceEndpointAddress(), // Get the first s value
                        db.getDbInstanceEndpointPort(), // Get the second s value
                        imageName // Use the image name as the database name
                    ));

                    envVars.put("SPRING_DATASOURCE_USERNAME", "admin_user"); // Set the database username.
                    envVars.put("SPRING_DATASOURCE_PASSWORD", db.getSecret().secretValueFromJson("password").toString()); // Set the database password from the secret.
                
                    envVars.put("SPRING_JPA_HIBERNATE_DDL_AUTO", "update"); // Set the Hibernate DDL auto configuration to update.
                    envVars.put("SPRING_SQL_INIT_MODE", "always"); // Set the SQL initialization mode to never.
                    envVars.put("SPRING_DATASOURCE_HIKERI_INICIALIZATION_FAIL_TIMEOUT", "60000"); // Time from the Hikari initialization timeout.
            }

            containerOptions.environment(envVars);
            taskDefinition.addContainer(imageName + "Container", containerOptions.build());

            return FargateService.Builder.create(this, id)
                .cluster(ecsCluster) // Associate the Fargate service with the ECS cluster.
                .taskDefinition(taskDefinition) // Associate the task definition with the Fargate service.
                .assignPublicIp(false) // Do not assign a public IP to the service.
                .serviceName(imageName) // Set the service name to the image name.
                .build(); // Build and return the Fargate service.
    }
    private void createApiGatewayService()
    {
        FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder
            .create(this, "APIGatewayTaskDefinition")
            .cpu(256) // Set the CPU units for the task.
            .memoryLimitMiB(512) // Set the memory limit for the task in MiB.
            .build();

        ContainerDefinitionOptions containerOptions = ContainerDefinitionOptions.builder()
            .image(ContainerImage.fromRegistry("api-gateway-patients"))
            .environment(Map.of("SPRING_PROFILES_ACTIVE", "prod",
                                "AUTH_SERVICE_URL", "http://host.docker.internal:8084"))              
            .portMappings(List.of(8083).stream()
            .map(port -> PortMapping.builder()
                .containerPort(port) // Specify the container port to be mapped.
                .hostPort(port) // Map the container port to the host port.
                .protocol(software.amazon.awscdk.services.ecs.Protocol.TCP) // Set the protocol to TCP.
                .build())
                .toList()) // Map the container ports to the specified ports.
            .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
            .logGroup(LogGroup.Builder.create(this,"ApiGatewayLogGroup")
                .logGroupName("/ecs/api-gateway-patients") // Set the log group name for the ECS service.
                .removalPolicy(RemovalPolicy.DESTROY) // Set the removal policy to destroy the log group when the stack is deleted.
                .retention(RetentionDays.ONE_DAY)
                .build())
            .streamPrefix("api-gateway-patients") // Set the log stream prefix to the image name.
            .build()))
        .build();

        // Add the container definition to the task definition.
        // This associates the container with the task definition, allowing it to run as part of the Fargate service.
        taskDefinition.addContainer("ApiGatewayContainer", containerOptions);

        ApplicationLoadBalancedFargateService apiGateway = ApplicationLoadBalancedFargateService.Builder
            .create(this, "APIGatewayService")
            .cluster(ecsCluster)
            .serviceName("api-gateway-service-container") // Associate the Fargate service with the ECS cluster.
            .taskDefinition(taskDefinition) // Associate the task definition with the Fargate service..serviceName
            .desiredCount(1) // Set the desired count of tasks to 1.
            .healthCheckGracePeriod(Duration.seconds(60)) // Set the health check grace period to 2 minutes.
            .build();    
    }

    // Main method to serve as the entry point for the application.
    public static void main(String[] args) 
    {
        // Create an instance of the App class, which represents the root of the CDK application.
        // AppProps.builder() is used to configure the application, such as specifying the output directory for synthesized templates.
        App app = new App(AppProps.builder().outdir("cdk.out").build());

        // Define stack properties using the StackProps builder.
        // Here, a BootstraplessSynthesizer is used, which allows the stack to be synthesized without requiring bootstrapping.
        StackProps props = StackProps.builder()
                                        .synthesizer(new BootstraplessSynthesizer())
                                        .build();

        // Create an instance of the LocalStack class, passing the app, a unique ID ("localstack"), and the stack properties.
        new LocalStack(app, "localstack", props);

        // Synthesize the application, which generates the CloudFormation templates in the specified output directory.
        app.synth();
        
        // Print a message to indicate successful initialization of the LocalStack.
        System.out.println("LocalStack initialized successfully.");
    }
}

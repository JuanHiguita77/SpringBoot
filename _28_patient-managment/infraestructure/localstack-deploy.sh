#!/bin/bash

set -e # Exit on error

aws --endpoint-url=http://localhost:4566 cloudformation deploy \
    --stack-name patient-management \
    --template-file "./cdk.out/localstack.template.json" 

aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text

# Run this with ./localstack-deploy.sh for charge the configuration to localstack or aws

# For check the logs: docker logs LocalStack > localstack.log


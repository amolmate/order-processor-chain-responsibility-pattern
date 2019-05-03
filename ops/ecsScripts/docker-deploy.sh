#!/bin/bash

env=$1

source ./app/wh/orderprocessor/awsScripts/config.cfg

if [[ ${env} == "prod" ]]; then
	eval $(aws ecr get-login --region eu-west-1 --profile ${prod_jenkinsawsuser} | sed 's|https://||')
	aws ecs register-task-definition --cli-input-json file://app/wh/orderprocessor/awsConfig/create-ecs-task-${env}.json --profile ${prod_jenkinsawsuser}
elif [[ ${env} == "dev" || ${env} == "cit" || ${env} == "sit" || ${env} == "uat" || ${env} == "pre" ]]; then

    eval $(aws ecr get-login --region eu-west-1 --profile ${nonprod_jenkinsawsuser} | sed 's|https://||')
    aws ecs register-task-definition --cli-input-json file://app/wh/orderprocessor/awsConfig/create-ecs-task-${env}.json --profile ${nonprod_jenkinsawsuser}
else
    exit
fi
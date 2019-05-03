#!/bin/bash

env=$1

source ./app/wh/orderprocessor/awsScripts/config.cfg

if [[ ${env} == "prod" ]]; then
    images_to_delete=$( aws ecr list-images --repository-name ${imagename} --filter "tagStatus=UNTAGGED" --query 'imageIds[*]' --output json --profile ${prod_jenkinsawsuser} )
	aws ecr batch-delete-image --repository-name ${imagename} --image-ids "$images_to_delete" --profile ${prod_jenkinsawsuser} || true
elif [[ ${env} == "dev" || ${env} == "cit" || ${env} == "sit" || ${env} == "uat" || ${env} == "pre" ]]; then
    images_to_delete=$( aws ecr list-images --repository-name ${imagename} --filter "tagStatus=UNTAGGED" --query 'imageIds[*]' --output json --profile ${nonprod_jenkinsawsuser} )
	aws ecr batch-delete-image --repository-name ${imagename} --image-ids "$images_to_delete" --profile ${nonprod_jenkinsawsuser} || true
else
    exit
fi
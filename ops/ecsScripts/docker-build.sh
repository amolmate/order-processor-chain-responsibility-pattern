#!/bin/bash

env=$1

if [[ ${env} == "dev" ]]; then
    source ./app/wh/orderprocessor/awsScripts/config.cfg
	eval $(aws ecr get-login --region eu-west-1 --profile ${nonprod_jenkinsawsuser} | sed 's|https://||')
	docker build -t ${nonprod_imagename}:${env} .
    docker push ${nonprod_imagename}:${env}

elif [[ ${env} == "sit" ]]; then
    source ./app/wh/orderprocessor/awsScripts/config.cfg
	eval $(aws ecr get-login --region eu-west-1 --profile ${nonprod_jenkinsawsuser} | sed 's|https://||')
    docker pull ${nonprod_imagename}:dev
	docker tag ${nonprod_imagename}:dev ${nonprod_imagename}:sit
	docker push ${nonprod_imagename}:sit

elif [[ ${env} == "uat" ]]; then
    source ./app/wh/orderprocessor/awsScripts/config.cfg
	eval $(aws ecr get-login --region eu-west-1 --profile ${nonprod_jenkinsawsuser} | sed 's|https://||')
	docker pull ${nonprod_imagename}:dev
	docker tag ${nonprod_imagename}:dev ${nonprod_imagename}:uat
	docker push ${nonprod_imagename}:uat

elif [[ ${env} == "pre" ]]; then
    source ./app/wh/orderprocessor/awsScripts/config.cfg
	eval $(aws ecr get-login --region eu-west-1 --profile ${nonprod_jenkinsawsuser} | sed 's|https://||')
    docker pull ${nonprod_imagename}:dev
	docker tag ${nonprod_imagename}:dev ${nonprod_imagename}:pre
	docker push ${nonprod_imagename}:pre

elif [[ ${env} == "prod" ]]; then
    source ./app/wh/orderprocessor/awsScripts/config.cfg
	eval $(aws ecr get-login --region eu-west-1 --profile ${prod_jenkinsawsuser} | sed 's|https://||')
	eval $(aws ecr get-login --region eu-west-1 --profile ${nonprod_jenkinsawsuser} | sed 's|https://||')
	
	docker pull ${prod_imagename}:prod
	docker tag ${prod_imagename}:prod ${prod_imagename}:prod-backup
	docker push ${prod_imagename}:prod-backup

	 docker pull ${nonprod_imagename}:dev
	docker tag ${nonprod_imagename}:dev ${prod_imagename}:prod
	docker push ${prod_imagename}:prod
else
    exit
fi
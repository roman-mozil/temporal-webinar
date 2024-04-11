#!/bin/bash

env="${1:-dev}"
proj_dir="."

project_name="$env-use1-temporal"


project_tag="nce-app-1.0"
echo "Building: $project_name:$project_tag"
docker buildx build --platform linux/amd64 -t "$project_name:$project_tag" .
#we can omit this step
docker tag "$project_name:$project_tag" "accountId.dkr.ecr.us-east-1.amazonaws.com/$env-use1-temporal:$project_tag"
#docker push "accountId.dkr.ecr.us-east-1.amazonaws.com/$env-use1-temporal:$project_tag"
echo "Done!"
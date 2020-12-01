#!/usr/bin/env bash
set -e

cd ../../docker/environment/prod

docker-compose pull
docker-compose up -d
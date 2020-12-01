#!/usr/bin/env bash
set -e

cd ../../docker/environment/dev

docker-compose pull
docker-compose up -d
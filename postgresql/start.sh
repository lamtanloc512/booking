#!/bin/bash

# Build the Docker image
docker build -t local-postgres .

# Run the Docker container
docker run -d -p 5432:5432 --name local-postgres-container local-postgres

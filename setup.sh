#!/bin/bash

echo "Running maven clean install..."
mvn clean install

if [ $? -ne 0 ]; then
    echo "Maven build failed. Exiting."
    exit 1
fi

echo "Changing directory to postgres..."
# shellcheck disable=SC2164
cd postgresql

if [ $? -ne 0 ]; then
    echo "Failed to change directory to postgres. Exiting."
    exit 1
fi

chmod +x build.sh
echo "Running the postgres script..."
./build.sh

if [ $? -ne 0 ]; then
    echo "Postgres script execution failed. Exiting."
    exit 1
fi


echo "Back to root directory"
cd ..

echo "Changing directory to service"
# shellcheck disable=SC2164
cd service
mvn liberty:dev

echo "Script completed successfully."

#!/bin/bash

echo "Integration test using MySQL on docker."

docker network create starwarsnet

( cd database ; ./run.sh )

sleep 5

( cd app ; ./run.sh )

sleep 5

( cd test ; ./run.sh )
 
 ./shutdown.sh
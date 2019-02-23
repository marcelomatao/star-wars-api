#!/bin/bash

docker build -t "starwarsapidb" .
docker run --rm --network=starwarsnet -d -p 3306:3306 --name "starwarsapidb" "starwarsapidb"
#docker run --rm -d -p 3306:3306 --name "starwarsapidb" "starwarsapidb"
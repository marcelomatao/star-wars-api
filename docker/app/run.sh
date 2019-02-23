#!/bin/bash

db_ip=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' starwarsapidb)
sed -i -e "s/[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}/$db_ip/g" ../../src/main/resources/application.properties

cd ../../ && mvn test && mvn clean package -DskipTests && cd docker/app

cp ../../target/star-wars-api-*.jar ./

docker build -t "starwarsapi" .

docker run --rm --network=starwarsnet -d -p 8080:8080 --name starwarsapi starwarsapi
#docker run --rm -d -p 8080:8080 --name starwarsapi starwarsapi
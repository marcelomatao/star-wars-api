docker rm -f $(docker ps -aqf "name=starwarsapidb")
docker rm -f $(docker ps -aqf "name=starwarsapi")

docker network rm starwarsnet
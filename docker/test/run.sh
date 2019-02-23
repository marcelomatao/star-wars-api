#!/bin/bash

status=$(curl -o /dev/null -s -w "%{http_code}\n" --header "Content-Type: application/json" \
  --request GET \
  http://localhost:8080/api/starwars/swapi/planets)

if [ $status -eq 200 ]; then
    echo "Listing Planets from swapi. Test ok."
else
    echo "Listing Planets from swapi error. Test error"
	exit 1
fi

status=$(curl -o /dev/null -s -w "%{http_code}\n" --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Jakku","terrain":"deserts","climate":"unknown"}' \
  http://localhost:8080/api/starwars/planet)

if [ $status -eq 201 ]; then
    echo "Planet created. Test ok."
else
    echo "Planet creation error. Test error"
	exit 1
fi

status=$(curl -o /dev/null -s -w "%{http_code}\n" --header "Content-Type: application/json" \
  --request DELETE \
  http://localhost:8080/api/starwars/planet/1)

if [ $status -eq 204 ]; then
    echo "Planet deleted. Test ok."
else
    echo "Planet deletion error. Test error"
	exit 1
fi

exit 0
FROM mariadb:10.3

ENV MYSQL_ROOT_USER root
ENV MYSQL_ROOT_PASSWORD root

COPY schema.sql /docker-entrypoint-initdb.d/

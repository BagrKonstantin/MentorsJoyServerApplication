FROM postgres:13.3
ENV PGDATA: "/var/lib/postgresql/data/pgdata"
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB world
COPY resources/init_functions/*.sql /docker-entrypoint-initdb.d/
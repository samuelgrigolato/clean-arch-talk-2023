FROM postgres
ENV POSTGRES_DB investments
ENV POSTGRES_PASSWORD 123456 
COPY *.sql /docker-entrypoint-initdb.d/
EXPOSE 5432


# Service-oriented distributed computing (SODC)
## Access to Windows subsystem for Linux (WSL)
    $ C:\Windows\System32\wsl.exe

## In Linux (WSL)

### 1. use docker to build image
    $ docker build -t user-service .

### start docker in daemon mode with port forwarding and service name user-service
    $ docker run -d -p 8081:8081 user-service

### How to find wsl ip
    $ hostname -I

#### Docker-compose: start docker image using docker-compose 
    $ docker-compose up --build


####
    $ sudo lsof -i :5432


#### To remove remains of previous images  (before restart)
    $ docker-compose down --volumes --remove-orphans

#### Postgresql / docker / queries / indexes
    $ docker run --name postgres-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=userdb -p 5432:5432 -d postgres
    $ docker exec -it postgres-db psql -U admin -d userdb
    $ CREATE INDEX idx_users_email ON users(email);
    $ SELECT indexname, indexdef FROM pg_indexes WHERE tablename = 'users';exit

#### prometheus
    $ http://localhost:9090/targets

    $  docker run -d -p 9090:9090 -v ./prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
    $  docker run -d --name prometheus   -p 9090:9090   -v /mnt/c/projects/study/sodc/sodc/prometheus.yml:/etc/prometheus/prometheus.yml   prom/prometheus

    $ ip route show default

    
#### Zipkin    
    $ http://localhost:9411/

####

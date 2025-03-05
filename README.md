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

#### To remove remains of previous images  (before restart)
    $ docker-compose down --volumes --remove-orphans

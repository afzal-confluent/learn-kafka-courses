FROM ubuntu:jammy

COPY ./.apt-dependencies.txt /tmp/dependencies.txt
RUN apt update && \
    xargs -a /tmp/dependencies.txt apt install --assume-yes --quiet 

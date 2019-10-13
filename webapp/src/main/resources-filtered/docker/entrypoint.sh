#!/bin/sh

#
# Docker swarm will mount any secrets into this directory
#
# The idea is to just define application.yaml as the secret
# As per https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files,
# this should be picked up automatically.
#
if [ -d /run/secrets ]
then
  cd /run/secrets || exit 1
fi

# strip off any prefix
ln -s *.application.yml application.yml

java -Djava.security.egd=file:/dev/./urandom -jar /app.jar


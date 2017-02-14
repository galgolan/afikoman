README for afikoman
==========================

# Setting up the server:
- Install nodejs
- Install with -g flag bower and grunt-cli
- clone the repo
- run npm install, bower install
- Install JRE, Maven
- Install MongoDB

# Building
- run `mvn install -Pprod -Dmaven.test.skip=true` (or fix the test)

# Running
- run `sudo nohup java -jar target/afikoman-0.0.1-SNAPSHOT.war &`
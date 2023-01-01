# syntax = docker/dockerfile:1.2
FROM clojure:tools-deps

COPY . /usr/src/google_ads
WORKDIR /usr/src/google_ads

RUN clj -T:build uber

CMD [ "java", "-jar", "target/google_ads-standalone.jar" ]



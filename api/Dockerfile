FROM frekele/gradle

ENV GROOVY_HOME /opt/groovy
ENV PATH=$GROOVY_HOME/bin:$PATH

WORKDIR /tmp
RUN wget https://dl.bintray.com/groovy/maven/apache-groovy-binary-2.4.8.zip \
  && unzip apache-groovy-binary-2.4.8.zip -d /opt \
  && ln -s /opt/groovy-2.4.8 $GROOVY_HOME

WORKDIR $GROOVY_HOME

RUN for f in `ls lib/groovy*.jar | cut -d/ -f2`; \
  do \
    k=`basename $f .jar` \
    && mv lib/$k.jar lib/$k-non-indy.jar \
    && cp indy/$k-indy.jar lib/$k.jar; \
  done


WORKDIR /app
ADD ./gradle gradle
ADD ./src src
ADD ./build.gradle build.gradle
ADD ./gradlew gradlew
ADD ./gradlew.bat gradlew.bat
ADD ./settings.gradle settings.gradle

ENV GRADLE_OPTS=-Dorg.gradle.daemon=false
RUN ./gradlew installDist

EXPOSE 80

CMD ["build/install/app/bin/./app"]

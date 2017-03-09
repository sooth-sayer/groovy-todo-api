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

EXPOSE 80

CMD ["./gradlew", "run"]

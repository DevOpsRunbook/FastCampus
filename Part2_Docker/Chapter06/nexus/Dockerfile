FROM sonatype/nexus3:3.32.0

USER root

RUN chown -R nexus:nexus ${NEXUS_HOME}/etc \
    && sed '/^application-port/s:$:\napplication-port-ssl=8443:' -i ${NEXUS_HOME}/etc/nexus-default.properties \
    && sed '/^nexus-args/s:$:,${jetty.etc}/jetty-https.xml:' -i ${NEXUS_HOME}/etc/nexus-default.properties \
    && rm -rf ${NEXUS_HOME}/etc/ssl && ln -s ${NEXUS_DATA}/etc/ssl ${NEXUS_HOME}/etc/ssl

COPY start.sh /usr/local/bin

USER nexus

EXPOSE 8443

CMD start.sh
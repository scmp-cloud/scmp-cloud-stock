FROM registry.cn-shanghai.aliyuncs.com/c7n/javabase:0.9.0
COPY app.jar /scmp-cloud-stock-admin.jar
COPY dist /dist/public
RUN chmod 777 /dist/public/env.sh
CMD /dist/public/env.sh java $JAVA_OPTS $SW_OPTS -jar /scmp-cloud-stock-admin.jar
FROM registry.cn-shanghai.aliyuncs.com/c7n/javabase:0.9.0
WORKDIR /work
ADD lib/google-chrome-stable_current_amd64.deb .
ADD lib/chromedriver_linux64.zip .
RUN apt update -y &&  apt --fix-broken -y install  ./google-chrome-stable_current_amd64.deb
RUN apt-get install -y ttf-wqy-zenhei ttf-dejavu
RUN unzip chromedriver_linux64.zip
RUN rm -rf google-chrome-stable_current_amd64.deb 
RUN rm -rf chromedriver_linux64.zip
ARG JAR_FILE 
ARG SERVICE_NAME
ENV JAR_FILE=${JAR_FILE}
ENV SERVICE_NAME=${SERVICE_NAME}
ENV MACULA_CLOUD_STOCK_CHROMELOCATION=/work/chromedriver
ADD target/${JAR_FILE} .
ENV SW_OPTS=${SW_OPTS} -Dskywalking.agent.service_name=$SERVICE_NAME -Dskywalking.agent.instance_uuid=$HOSTNAME
ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} ${SW_OPTS}  -Djava.security.egd=file:/dev/./urandom -jar ${JAR_FILE}" ]
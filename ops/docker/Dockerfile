FROM morrisons/loyalty-clubs:morrisonJava8base

MAINTAINER McColls team

# Make application directories
RUN mkdir -p /app/wh/orderprocessor/bin && \
    mkdir -p /app/wh/orderprocessor/config && \
    mkdir -p /app/wh/orderprocessor/logs && \
    mkdir -p /app/wh/orderprocessor/awsConfig &&\
    mkdir -p /app/wh/orderprocessor/awsScripts

# Copy application build artifacts (configs for all envs passed in; change which with docker run command argument)
ADD app app/

# Run the application
WORKDIR /app/wh/orderprocessor/bin
ENTRYPOINT  ["/bin/bash"]
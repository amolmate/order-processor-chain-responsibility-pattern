FROM morrisons/loyalty-clubs:morrisonJava8base

MAINTAINER McColls team

# Make application directories
RUN mkdir -p /app/wh/orderreconciliation/bin && \
    mkdir -p /app/wh/orderreconciliation/config && \
    mkdir -p /app/wh/orderreconciliation/logs && \
    mkdir -p /app/wh/orderreconciliation/awsConfig &&\
    mkdir -p /app/wh/orderreconciliation/awsScripts

# Copy application build artifacts (configs for all envs passed in; change which with docker run command argument)
ADD app app/

# Run the application
WORKDIR /app/wh/orderreconciliation/bin
ENTRYPOINT  ["/bin/bash"]
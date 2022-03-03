FROM node:14.19-slim
WORKDIR /aaas-ui
ARG BACKEND_APP_URL
ARG SSL_CERT_PATH
ARG SSL_KEY_PATH

COPY package.json ./
RUN npm install
COPY . .
RUN echo REACT_APP_BACKEND_URL=${BACKEND_APP_URL} > .env && \
    echo HTTPS=true >> .env && \
    echo SSL_CRT_FILE=${SSL_CERT_PATH} >> .env && \
    echo SSL_KEY_FILE=${SSL_KEY_PATH} >> .env
EXPOSE 3000
CMD ["npm", "start"]

FROM node:14.19-slim

WORKDIR /aaas-ui
ARG BACKEND_APP_URL
ARG DATA_REFRESH_RATE
ARG MAP_TILER_KEY
ARG SSL_CERT_PATH
ARG SSL_KEY_PATH

COPY package.json ./
RUN npm install
COPY . .
RUN echo REACT_APP_BACKEND_URL=${BACKEND_APP_URL} > .env && \
    echo REACT_APP_DATA_REFRESH_RATE=${DATA_REFRESH_RATE} >> .env && \
    echo REACT_APP_MAP_TILER_KEY=${MAP_TILER_KEY} >> .env && \
    echo HTTPS=true >> .env && \
    echo SSL_CRT_FILE=${SSL_CERT_PATH} >> .env && \
    echo SSL_KEY_FILE=${SSL_KEY_PATH} >> .env

EXPOSE 3000
CMD ["npm", "start"]

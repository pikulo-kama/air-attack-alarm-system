FROM node:14.19-slim

WORKDIR /aaas-ui
ARG BACKEND_APP_URL

COPY package.json ./
RUN npm install
COPY . .
RUN echo REACT_APP_BACKEND_URL=${BACKEND_APP_URL} > .env

EXPOSE 3000
CMD ["npm", "start"]

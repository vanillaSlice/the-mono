FROM node:11.10.0-alpine as builder
WORKDIR /app
COPY package.json .
RUN npm install
COPY ./src /app/src
COPY ./public /app/public
RUN npm run build

FROM nginx:1.15.9-alpine
EXPOSE 80
COPY --from=builder /app/build /usr/share/nginx/html

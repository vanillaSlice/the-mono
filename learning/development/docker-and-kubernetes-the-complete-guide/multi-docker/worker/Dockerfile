FROM node:11.10.0-alpine
WORKDIR /app
COPY package.json .
RUN npm install
COPY index.js .
COPY keys.js .
CMD ["npm", "start"]

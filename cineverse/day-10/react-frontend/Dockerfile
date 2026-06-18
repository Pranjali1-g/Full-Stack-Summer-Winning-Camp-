# Use official Node runtime base image [cite: 74]
FROM node:18
WORKDIR /app

# Copy package management files first to cache dependencies [cite: 76]
COPY package.json .
RUN npm install [cite: 77]

# Copy the remaining source files and compile the static build [cite: 78, 79]
COPY . .
RUN npm run build

# Expose port and start the React application development server [cite: 80]
EXPOSE 3000
CMD ["npm", "start"]
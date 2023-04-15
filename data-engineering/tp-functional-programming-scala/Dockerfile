FROM mozilla/sbt:8u292_1.5.7

WORKDIR /app

# Copy the source code into the container
COPY . .

# Compile the code
RUN sbt compile

# Run the tests
CMD ["sbt", "test"]
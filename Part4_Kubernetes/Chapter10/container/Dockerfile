FROM golang:1.16-alpine

# Set destination for COPY
WORKDIR /app

# Download Go modules
COPY go.mod .
COPY go.sum .
RUN go mod download

COPY edit.html .
COPY view.html .

# Copy the source code.
COPY *.go ./

# Build
RUN go build -o /web-text-box

# To actually open the port, runtime parameters
EXPOSE 8080

# Run
CMD [ "/web-text-box" ]

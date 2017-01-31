#!/bin/bash

echo "Starting mongo..."
docker run --name mongo -d -it -p 27017:27000 mongo

#!/bin/bash
set -e

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
LAMBDA_DIR="$BASE_DIR/../lambda"

if [ ! -f "$LAMBDA_DIR/authorizer.js" ]; then
  echo "❌ Arquivo authorizer.js não encontrado em $LAMBDA_DIR"
  exit 1
fi

mkdir -p /tmp/lambda
zip -j /tmp/lambda/authorizer.zip "$LAMBDA_DIR/authorizer.js"

awslocal lambda create-function \
  --function-name jwt-authorizer \
  --runtime nodejs18.x \
  --handler authorizer.handler \
  --role arn:aws:iam::000000000000:role/lambda-role \
  --zip-file fileb:///tmp/lambda/authorizer.zip || true

echo "✅ Lambda Authorizer criada com sucesso no LocalStack!"

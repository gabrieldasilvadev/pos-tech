#!/bin/bash
set -e

cd "$(dirname "$0")/lambda"

echo "🧩 Instalando dependências do Authorizer..."
npm install --omit=dev >/dev/null 2>&1

mkdir -p zip

echo "📦 Gerando ZIP do Lambda Authorizer..."
zip -r zip/authorizer.zip authorizer.js node_modules package.json >/dev/null

echo "✅ Lambda Authorizer empacotado em: lambda/zip/authorizer.zip"

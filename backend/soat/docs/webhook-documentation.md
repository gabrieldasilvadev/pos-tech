# Documentação do Webhook MercadoPago

Este documento descreve o endpoint webhook para recebimento de notificações de pagamento do MercadoPago.

## Endpoint do Webhook

```
POST /webhooks/mercado-pago
```

## Formato da Requisição

O endpoint webhook espera uma requisição form-encoded com os seguintes parâmetros:

| Parâmetro | Tipo   | Descrição                                                    |
|-----------|--------|--------------------------------------------------------------|
| id        | String | O ID do pagamento relacionado a esta notificação            |
| topic     | String | O tipo de notificação. Deve ser "payment" para processamento |

## Exemplo de Requisição

```
POST /webhooks/mercado-pago
Content-Type: application/x-www-form-urlencoded

id=550e8400-e29b-41d4-a716-446655440000&topic=payment
```

## Resposta

O endpoint webhook retornará uma resposta `200 OK` se a notificação foi recebida com sucesso, independentemente de
o pagamento ter sido processado com sucesso ou não.

## Lógica de Processamento

Quando uma notificação webhook é recebida:

1. O sistema verifica se o `topic` é "payment"
2. Se for, o sistema recupera os detalhes do pagamento usando o parâmetro `id`
3. O sistema analisa o status do pagamento da resposta do gateway
4. O status do pagamento é atualizado baseado na resposta do gateway:
   - **APPROVED**: Pagamento é marcado como `FINISHED`
   - **DECLINED**: Pagamento é marcado como `DECLINED`
   - **FAILED**: Pagamento é marcado como `FAILED`
5. O pagamento atualizado é salvo no repositório

## Fluxo de Status do Pagamento

O webhook suporta as seguintes transições de status de pagamento:

### Fluxo de Pagamento Bem-sucedido
```
PENDING → APPROVED → FINISHED (via webhook)
```

### Fluxo de Pagamento Recusado
```
PENDING → DECLINED (via webhook)
```

### Fluxo de Pagamento com Falha
```
PENDING → FAILED (via webhook)
```

## Tratamento de Erros

Se ocorrer um erro durante o processamento, o sistema registrará o erro mas ainda retornará uma resposta `200 OK` para confirmar
o recebimento da notificação. Isso é para evitar que o MercadoPago tente reenviar a notificação desnecessariamente.

## Testando o Webhook

Você pode testar o endpoint webhook usando curl:

```bash
curl -X POST \
  http://localhost:8080/webhooks/mercado-pago \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'id=550e8400-e29b-41d4-a716-446655440000&topic=payment'
```

Ou usando uma ferramenta como Postman:

1. Configure o método da requisição para `POST`
2. Configure a URL para `http://localhost:8080/webhooks/mercado-pago`
3. Configure o header Content-Type para `application/x-www-form-urlencoded`
4. Adicione os seguintes parâmetros de formulário:
    - `id`: Um ID de pagamento válido
    - `topic`: "payment"
5. Envie a requisição

## Simulando Diferentes Cenários

A implementação FakeCheckoutClient suporta simulação de diferentes cenários de pagamento:

### Cenários Disponíveis
- **Sucesso (Aprovado)**: Processamento normal resultando em pagamento aprovado
- **Recusado**: Pagamento é recusado pelo gateway
- **Falha**: Falha técnica durante o processamento do pagamento
- **Timeout**: O gateway de pagamento lança uma exceção de timeout

### Propriedades de Configuração

Estes cenários podem ser configurados usando as seguintes propriedades:

```properties
# Habilitar/desabilitar simulação de atraso de rede
payment.gateway.simulation.delay.enabled=true

# Faixa de atraso de rede em milissegundos
payment.gateway.simulation.delay.min=100
payment.gateway.simulation.delay.max=1000

# Taxas de simulação de falha (0.0 = nunca, 1.0 = sempre)
payment.gateway.simulation.failure.rate=0.1    # 10% de chance de falha técnica
payment.gateway.simulation.timeout.rate=0.05   # 5% de chance de timeout
payment.gateway.simulation.decline.rate=0.15   # 15% de chance de recusa de pagamento
```

### Testando Diferentes Resultados

Para testar diferentes resultados de pagamento:

1. **Testar Pagamento Aprovado**: A maioria das chamadas webhook resultará em pagamentos aprovados
2. **Testar Pagamento Recusado**: Algumas chamadas webhook resultarão aleatoriamente em pagamentos recusados
3. **Testar Pagamento com Falha**: Algumas chamadas webhook resultarão aleatoriamente em pagamentos com falha

O resultado real é determinado aleatoriamente baseado nas taxas configuradas quando o webhook processa a notificação.
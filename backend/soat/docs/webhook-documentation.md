# MercadoPago Webhook Documentation

This document describes the webhook endpoint for receiving payment notifications from MercadoPago.

## Webhook Endpoint

```
POST /webhooks/mercado-pago
```

## Request Format

The webhook endpoint expects a form-encoded request with the following parameters:

| Parameter | Type   | Description                                                |
|-----------|--------|------------------------------------------------------------|
| id        | String | The payment ID that this notification is related to        |
| topic     | String | The type of notification. Must be "payment" for processing |

## Example Request

```
POST /webhooks/mercado-pago
Content-Type: application/x-www-form-urlencoded

id=550e8400-e29b-41d4-a716-446655440000&topic=payment
```

## Response

The webhook endpoint will return a `200 OK` response if the notification was received successfully, regardless of
whether the payment was processed successfully or not.

## Processing Logic

When a webhook notification is received:

1. The system checks if the `topic` is "payment"
2. If it is, the system retrieves the payment details using the `id` parameter
3. The payment status is updated to `FINISHED` if the current status is `APPROVED`
4. If the payment status is not `APPROVED`, an exception is thrown

## Error Handling

If an error occurs during processing, the system will log the error but still return a `200 OK` response to acknowledge
receipt of the notification. This is to prevent MercadoPago from retrying the notification unnecessarily.

## Testing the Webhook

You can test the webhook endpoint using curl:

```bash
curl -X POST \
  http://localhost:8080/webhooks/mercado-pago \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'id=550e8400-e29b-41d4-a716-446655440000&topic=payment'
```

Or using a tool like Postman:

1. Set the request method to `POST`
2. Set the URL to `http://localhost:8080/webhooks/mercado-pago`
3. Set the Content-Type header to `application/x-www-form-urlencoded`
4. Add the following form parameters:
    - `id`: A valid payment ID
    - `topic`: "payment"
5. Send the request

## Simulating Different Scenarios

The FakeCheckoutClient implementation supports simulating different scenarios:

- Success: Normal processing of the payment
- Failure: The payment gateway returns an empty response
- Timeout: The payment gateway throws an exception

These scenarios can be configured using the following properties:

```properties
payment.gateway.simulation.delay.enabled=true
payment.gateway.simulation.delay.min=100
payment.gateway.simulation.delay.max=1000
payment.gateway.simulation.failure.rate=0.1
payment.gateway.simulation.timeout.rate=0.05
```
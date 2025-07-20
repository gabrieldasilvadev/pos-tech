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
3. The system parses the payment status from the gateway response
4. The payment status is updated based on the gateway response:
   - **APPROVED**: Payment is marked as `FINISHED`
   - **DECLINED**: Payment is marked as `DECLINED`
   - **FAILED**: Payment is marked as `FAILED`
5. The updated payment is saved to the repository

## Payment Status Flow

The webhook supports the following payment status transitions:

### Successful Payment Flow
```
PENDING → APPROVED → FINISHED (via webhook)
```

### Declined Payment Flow
```
PENDING → DECLINED (via webhook)
```

### Failed Payment Flow
```
PENDING → FAILED (via webhook)
```

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

The FakeCheckoutClient implementation supports simulating different payment scenarios:

### Available Scenarios
- **Success (Approved)**: Normal processing resulting in approved payment
- **Decline**: Payment is declined by the gateway
- **Failure**: Technical failure during payment processing
- **Timeout**: The payment gateway throws a timeout exception

### Configuration Properties

These scenarios can be configured using the following properties:

```properties
# Enable/disable network delay simulation
payment.gateway.simulation.delay.enabled=true

# Network delay range in milliseconds
payment.gateway.simulation.delay.min=100
payment.gateway.simulation.delay.max=1000

# Failure simulation rates (0.0 = never, 1.0 = always)
payment.gateway.simulation.failure.rate=0.1    # 10% chance of technical failure
payment.gateway.simulation.timeout.rate=0.05   # 5% chance of timeout
payment.gateway.simulation.decline.rate=0.15   # 15% chance of payment decline
```

### Testing Different Outcomes

To test different payment outcomes:

1. **Test Approved Payment**: Most webhook calls will result in approved payments
2. **Test Declined Payment**: Some webhook calls will randomly result in declined payments
3. **Test Failed Payment**: Some webhook calls will randomly result in failed payments

The actual outcome is determined randomly based on the configured rates when the webhook processes the notification.
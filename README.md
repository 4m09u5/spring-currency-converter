# What does this service do?
The service provides an API for converting currencies. It takes acronyms for two currencies and quantity of currency to be converted.

## Request requirements
To convert currency, you should send a GET request to the following endpoint:

`/api/v1/currency/convert`

The server expects you to provide three parameters:
* from - acronym for the currency to be converted
* to - acronym for desired representation
* value - a rational number that represents the quantity of money

## Response structure
The response is a JSON object that stores the equivalent quantity of money in new currency and can be accessed using the `converted` key.

> [!WARNING]
> The service is still under development and may not work properly. Please report any incorrect service behavior.

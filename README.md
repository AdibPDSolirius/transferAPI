<h2>transferAPI</h2>

Make a PUT request to http://localhost:8080/transfer
<br><br>
Payload: 
<pre>
  Schema
  { 
    senderID: BigInteger that is non-negative and not the same as receiverID
    receiverID: BigInteger that is non-negative and not the same as senderID
    amount: BigDecimal that is greater than zero
  }
  
  Will return 400 status if schema is violated
  
  Example
  {
    senderID: 1,
    receiverID: 2,
    amount: 50
  }
  
  
</pre>

Response:
<pre>
  Schema:
  {
    status: String,
    message: String
  }
  
  Status options: 
    Success | Failure
  Message options:
    Successful Transfer(200) | Insufficient Funds(409) | Sender account not found(422) | Receiver account not found(422)
</pre>

Database initialised with two accounts
<pre>
  Account A: 
    ID: 0
    Balance: 100
   
  Account B:
    ID: 1
    Balance: 100
</pre>

Design and Flow

![Imgur Image](https://i.imgur.com/vLIpq1C.jpg)


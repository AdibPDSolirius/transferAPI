<h2>transferAPI</h2>

Post to https://localhost:8080/transfer
<br><br>
Payload: 
<pre>
  Schema
  { 
    senderId: BigInteger that is not less than zero and not the same as receiver id
    receiverId: BigInteger that is not less than zero and not the same as sender id
    amountId: BigDecimal that is greater than zero
  }
  
  Will return 400 status if schema is violated
  
  Example
  {
    senderId: 1,
    receiverId: 2,
    amountId: 50
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
    ID: 1
    Balance: 100
   
  Account B:
    ID: 2
    Balance: 100
</pre>

Design and Flow

[Imgur](https://i.imgur.com/dAsBn5W.jpg)


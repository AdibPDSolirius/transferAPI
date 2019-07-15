<h2>transferAPI</h2>

Post to https://localhost:8080/transfer
<br><br>
Payload: 
<pre>
  Schema
  { 
    senderId: BigInteger,
    receiverId: BigInteger,
    amountId: BigDecimal
  }
  
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


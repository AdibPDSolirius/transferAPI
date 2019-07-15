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

Database initialised with two accounts
<pre>
  Account A: 
    ID: 1
    Balance: 100
   
  Account B:
    ID: 2
    Balance: 100
</pre>
1: 100
2: 100

Design and Flow


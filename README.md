# ChatApplication
java multithreaded chat server 

## DESCRIPTION
**ChatApllication is a simple server/client multithread program.
The main idea is clients to send messages to all connected clients 
or send messages to specific client. ChatApllication is writtend 
in Java using Socket API.**

### Here are the points, in the server:  

 - Keeping a list of connected clients.  
 - Defining a thread, for server input.  
 - Defining a queue of the received messages.  
 - A thread polling from the queue, and work with it.  
 - Some utility methods for sending messages.  

### And for client:
- Running thread for reading messages.  
- Running thread for writing messages.

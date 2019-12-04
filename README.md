# CustomerService
/* This project models a group of customers and a service-counter for a 9-5 work shift.
The expected input is two text files: 
1. customers text file containing the customer number and arrival time for each, along with a constant service time per customer.
2. query file containing the information wanted
The output is the query text file, but with the answers at the end of each line
Methods contain waiting time of a customer, number of customers served, longest break time, total break time, and maximum number of people in queue
Customers arriving before 9 or after 5 are not served. Some customers arriving before 5 may not be served if the queue is too long.
The code utilizes linked list for the customer queue.

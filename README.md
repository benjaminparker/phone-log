A coding exercise from an interview which wanted an implementation of a phone log pricer.

The phone logs are in the format


123-456-789,02:03:17
123-456-753,00:01:52
123-456-777,00:13:02

Where the first string is the dialled phone no and the second the time as hh:mm:ss

Pricing is done as follows

< 5 minutes = seconds * 3

>= 5 minutes = partial minutes * 150

Additionally if more than one number is called then the most frequently called number has zero charge for all calls.

# Brief Overview

Karsten's backend application server is intended to be split into numerous subapplications.  
The design choice was made to prevent definition overload of domain objects, as well as keeping boundaries explicit.  
Each subapplication should communicate with each other and with clients through APIs.  

This repository is concerned with the processing and storage of Transactions. Transactions represent the day to day inflows and outflows between the various accounts.  

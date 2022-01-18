# DigiClean
A School project to create a dual web and USSD application that connects clients with casual domestic cleaners offering various cleaning services.


## USSD
- Install Ngrok 
  `https://ngrok.com/download`
- Host the ussd code
   `cd Digiclean && php -s 0.0.0.0:<port>`
- Forward the open port to WAN
   `./ngrok http <port>`
- create a channel on AT's dashboard and test it using the ussd simulator
    `https://account.africastalking.com/apps/sandbox/ussd/channel/create`
     `https://simulator.africastalking.com:1517/simulator/ussd`
## web
- run the code using maven or gradle
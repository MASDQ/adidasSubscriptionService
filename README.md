# adidasSubscriptionService
Subscription Service for the Adidas Technical Challange
It Receive request from PublicService and makr CRUD operations over Subscriptions
If is required, it also send a email to the Subscription email to confirm the registration process

It have a bug which, instead of receive Subscription objets or List<String>, get LinkedHashMap<String, LinkedHashMap<String, List<String>>, it affect to "CreateSubcription" and "RecoverManySubscription" flows.
So on, there is an error then PublicService make a "GetManySubscription" request, it make a Get petiton and send a body, but the SubscriptionService dont receive it.
Both problems whoult be fixed on future versions.

There whoul be neccessary to perform functional testing and unitary testing, due technical problems some request have problems to manage data from DataBase.

A improved security layer would be added on futher versions.

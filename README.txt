Cake Manager Micro Service (fictitious)
=======================================

Requirements:
* By accessing the root of the server (/) it should be possible to list the cakes currently in the system. This must be presented in an acceptable format for a human to read.

* It must be possible for a human to add a new cake to the server.

* By accessing an alternative endpoint (/cakes) with an appropriate client it must be possible to download a list of
the cakes currently in the system as JSON data.

* The /cakes endpoint must also allow new cakes to be created.

Comments:
* We feel like the software stack used by the original developer is quite outdated, it would be good to migrate the entire application to something more modern.
* Would be good to change the application to implement proper client-server separation via REST API.

Bonus points:
* Tests
* Authentication via OAuth2
* Continuous Integration via any cloud CI system
* Containerisation


This API implementation is built from scratch using Spring Boot and gradle build tools.
I've separated the code into packages which handles the data persistence, process (service), Controller and Entity.
From the requirements provided above, this is what I've managed to complete - I've focused initially on building the
project and making the CORE functionalities work - I've only capped my time working on this otherwise it would take me longer
to complete (similar to how things are in real life).

1. Root server (/) is listing the cakes currently in the system presented in a table (/) - I've added functionality of a search in repo
and only get the list from the link provided if nothing is stored in the repo (this reduces process time root server needs to process
all data for succeeding calls) - alternatively, this list can also be accessed through /retrieveCakes
Add Cake functionality could also be accessed through this ROOT server
2. Add new cake function is available through /addCake - function tested via POSTMAN (not available via UI provided - I've added a form in the landingPage
that isn't fully implemented yet - as data needs to be binded to a JSON object for it to work)
    Sample inpuit:
    {
        "title" : "Banoffee cheesecake",
        "desc" : "A Banana caramel cheesecake",
        "image" : "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"
    }
    This method returns a full list of cakes including the newly added one
3. Download of the list of cakes (in JSON format) available through /cakes endpoint - this however doesn't automatically redirect to
a page where add cake is implemented
4. Migrate application to a more modern way
5. Proper client - server separation (but to show the output I've added the html classes within the same project)
6. Unit test cases done for service (Not started)


NOTE: Simple UI provided and linked in to the controller just so output is easier seen (but couple of tweak can make this fully a server side system)
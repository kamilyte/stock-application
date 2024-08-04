<br />
<p align="center">
  <h1 align="center">Stock Market Simulation</h1>

  <p align="center">
    < add a very short description here (1 sentence)>
  </p>
</p>

## Table of Contents

* [About the Project](#about-the-project)
    * [Built With](#built-with)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Installation](#installation)
    * [Running](#running)
* [Modules](#modules)
* [Notes](#notes)
* [Evaluation](#evaluation)
* [Extras](#extras)

## About The Project

<!-- Add short description about the project here -->

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
* [Maven 3.6](https://maven.apache.org/download.cgi) or higher

### Installation

1. Navigate to the `stocks` directory
2. Clean and build the project using:

```sh
mvn install
```

### Running

1. Run main method in "Stock application" module to start a message queue server.
2. Run main method in "Trader application" module to start the traderBots.

## Modules

#### Command

Implements the command and factory patterns which is used to make and execute commands for the message queue and stock
orders. By creating command handler, command and command factory interfaces we decouple actual command execution and
invoking commands. It also increases code reusability since it makes it simple to add new commands without changing the
whole structure of the project. This model is being used in message-queue and stock application.

#### Message queue

Implements a thread-safe message queue which is working over the network. Thread safe queue is the main part of the
module and it keeps all the orders sent by different trader bots sorted by their time stamp. It also has a Message
Logger which implements Message handler from the networking module so that each message that comes from different
clients could be executed. It receives messages as JSON strings and then by using custom JSON adapters transforms it to
actual message and vice versa. We also have an Abstract message queue command interface so that if in the future we want
to add more functions to the message queue we could do it very easily.

#### Networking

This module consists of 2 main parts: the server side and the client side.
Server side is responsible for one and only server which is going to handle all the client connections. To not execute
it directly it also has a client handler, which handles the communication between client and a server.
Client side is responsible for all the clients that are connected to a server. Connection is formed by a client
connecting to a socket. This module is used for message queue and stock application implementation.

#### Stock application

Implements a stock market simulation between traders and actual stocks. This is the server-side of the application, so
all the orders that come from different traders are processed here. Therefore, there is an order command factory which
creates commands “Buy” and “Sell”. Besides that we have another message handler, which is not working over server, but
it is dealing with actual stocks information and different orders. Stock application constantly polls messages from the
message queue and executes orders.

#### Stock market UI

This module is just a simple UI to see our stock market simulation.

#### Stocks-Main

This module is responsible for processing actual stock and traders information. It is used in both stock application and
trader application, since StockManager works like a model or “skeleton” for the whole stock simulation.

#### Trader-application

This module is responsible for creating the bots that will interact with the stock application and will put messages
onto the queue, requesting whether they want to buy or sell a stock. The bots randomly generate what stock they will buy
and how much of it, and each random generation of an order will create a new thread and then push onto a thread queue
where the stock application will handle the order. The order is communicated over a network.

## Design

#### Command pattern

The command pattern encapsulates all the behaviour required to carry out an order. It decouples modules so we avoid
creating a circular dependency. In our program, it allows us to handle messages (orders) buy communicating them over the
network to the message queue where it can sort the messages and then once the messages are polled, the message is
communicated over network to stock application where it can handle the message based off of its command.

#### Singleton pattern

The singleton pattern restricts instantiation of a class and makes sure that only one instance of the class exists in
the whole java application. This is so a thread pool can be created so multi-threading is possible and where all the
threads will be able to be executed by the stock application. The stock manager class uses the singleton pattern so only
one instance of it is created so data can be kept the same and updated no matter in which module the manager is used in.
It allows for data to stay consistent whenever there are updates to the class.

#### Factory pattern

The factory pattern allows us to create an interface for creating a “factory” of related objects without specifying the
classes. We use a factory in stock application and message queue. In stock application the factory is useful for
registering the buy/sell commands into the command handler where the command will be handled. It is used in combination
with command pattern to communicate behaviour over modules.

#### Layers

The layer pattern is where the responsibilities of program are divided among different layers of the application. In our
program that will be the use of modules. It reduces dependency as the functions of each module is separate from the
others, and instead they use interfaces to share behaviours with each other. It also makes testing easier as we can test
each module separately.

## Evaluation

Our implementation is stable. The stocks and traders get loaded into our program and the trader bot orders get
communicated over the network and are handled as they should be by the relevant methods by implementing interfaces. The
stock application has the ability to resolve orders between traders, however, they are resolved fully rather than
partially.

Not everything is tested. We didnt have enough time to do tests and make sure that they worked. Additionally, we had
problems with testing due to the test file not being able to load the yaml files which make it very difficult to make
tests, as StockManager is used in every single test.
With enough time, we would have been able to figure this out.

We were able to complete all the requirements, except creating a transaction list to keep track off all the transactions
of a stock. However, if we had the time, we would have been able to do that and we would have made the logic to our buy
and sell orders more specific. This is because our buy/sell methods are created so the transactions are fully resolved,
which doesnt allow for transactions to be partially resolved. While we know that transactions can change the price of
the stock, what trader has bought a stock and removed a stock, this isnt able to be shown properly in the stock market
user interface as the bots are not creating orders which fits the conditions of which the orders will be fully resolved,
rather than partially. While the code doesnt work 100% as we would have liked it to, we wouldnt have done anything
completely differently other than refining the logic for the stock application and finishing off tests. 
<!--
Discuss the stability of your implementation. What works well? Are there any bugs? Is everything tested properly? Are there still features that have not been implemented? Also, if you had the time, what improvements would you make to your implementation? Are there things which you would have done completely differently? Try to aim for at least 250 words.
-->

## Extras

<!--
If you implemented any extras, you can list/mention them here.
-->

___


<!-- Below you can find some sections that you would normally put in a README, but we decided to leave out (either because it is not very relevant, or because it is covered by one of the added sections) -->

<!-- ## Usage -->
<!-- Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources. -->

<!-- ## Roadmap -->
<!-- Use this space to show your plans for future additions -->

<!-- ## Contributing -->
<!-- You can use this section to indicate how people can contribute to the project -->

<!-- ## License -->
<!-- You can add here whether the project is distributed under any license -->


<!-- ## Contact -->
<!-- If you want to provide some contact details, this is the place to do it -->

<!-- ## Acknowledgements  -->

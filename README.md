Controls the Transaction Bounded Context of Karsten.

# Structure

Domain Layer: contains the domain objects
Application Layer: handles use case logic.
Infrastructure Layer: communicates with external parties, which are the repository, the GUI client and other Bounded Contexts.

Communication is done with JeroMQ, an implementation of the ZeroMQ library.

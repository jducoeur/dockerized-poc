# Querki Dockerized Proof of Concept

Querki is still built on top of, frankly, antique technology for
its underlying infrastructure. We need to get that up to date ASAP,
before the whole thing falls over.

So this is an experimental proof of concept -- a tiny distributed
Play / Akka Cluster application built on top of raw AWS mechanisms
for handling deployment, discovery, and all that.

Once this has all the critical features we need and has proven
itself out, it will be used as a model to redo Querki's machinery
in a more modern way.
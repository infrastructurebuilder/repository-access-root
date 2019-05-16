# repository-access-root

Access to Maven artifcats auto-configured by settings.xml

One of IB's main goals is to allow us to build tooling around artifacts acquired from a repository.  This component allows us to
fetch artifacts using the `settings.xml` file, much the same way Maven would.

All of this has quite a large dependency graph.  Use it with caution.

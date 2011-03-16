# The website of the Clojure Group Hamburg.

## Usage


###local development
run `(net.clj-hh.local/run-local)` from a repl.


###development server (to check in a more "real" enviorment before deploying)
run `build/dev_server.sh`.

###deploying to the appengine
build the needed files using `lein gae prepare`

then run `build/deploy.sh`

## License

Copyright (C) 2010

Distributed under the Eclipse Public License, the same as Clojure.

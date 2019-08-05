# artifact-services-impl

Implementations for interacting with artifact repositories

** Unit/Integration testing requirements have changed **

## How To Use

First off, don't use this directly.  Use `artifact-services-via-settings-component` .

If you're not prone to following that particular advice, here's some data:

```
gavImpl coords = new DefaultGAV("junit","junit","3.8.1"); // jar & no classifier

Properties props = new Properties();
// TODO Add the appropriate properties here

String localRepositoryPath = "/home/me/.m2/repository";
String validatingUsername = null;
String validatingPassword = null;
String remoteRepositoryUtl = ArtifactServices.CENTRAL_REPO_URL;
String normalizeSnapshots = true;
ArtifactServices utils = new DefaultArtifactServices(localRepositoryPath, validatingUsername,
      validatingPassword, remoteRepositoryUrl, normalizeSnapshots);

List<File> l = utils.getDependencies(coords, JavaScopes.COMPILE);
```

## Testing

The tests read you local settings file for test data.

To run the tests for this code, you must have a `settings.xml` that has the following:
* A `<server>` with `<id>test</id>` that has fake username and password.

`ArtifactServicesImplTest.setUp()` describes one way to get an `ArtifactServices` instance.
start locator --name=locator1
Starting a GemFire Locator in C:\Users\V1014352\Documents\vmware-gemfire-10.1.2\bin\locator1...
The Locator process terminated unexpectedly with exit status 1. Please refer to the log file in C:\Users\V1014352\Documents\vmware-gemfire-10.1.2\bin\locator1 for full details.

Exception in thread "main" com.vmware.gemfire.deployment.modules.internal.LauncherException: java.lang.RuntimeException: An IO error occurred while starting a Locator in C:\Users\V1014352\Documents\vmware-gemfire-10.1.2\bin\locator1 on SBIEPAYBD173.CORP.AD.SBI[10334]: Network is unreachable; port (10334) is not available on localhost.
    at gemfire//com.vmware.gemfire.deployment.modules.internal.Launcher.launch(Launcher.java:90)
    at gemfire//com.vmware.gemfire.deployment.modules.internal.Launcher.main(Launcher.java:103)
    at org.jboss.modules.Module.run(Module.java:353)
    at org.jboss.modules.Module.run(Module.java:321)
    at org.jboss.modules.Main.main(Main.java:604)
    at com.vmware.gemfire.bootstrap.internal.Launcher.launch(Launcher.java:64)
    at com.vmware.gemfire.bootstrap.LocatorLauncher.main(LocatorLauncher.java:31)
Caused by: java.lang.RuntimeException: An IO error occurred while starting a Locator in C:\Users\V1014352\Documents\vmware-gemfire-10.1.2\bin\locator1 on SBIEPAYBD173.CORP.AD.SBI[10334]: Network is unreachable; port (10334) is not available on localhost.
    at gemfire//org.apache.geode.distributed.LocatorLauncher.start(LocatorLauncher.java:808)
    at gemfire//org.apache.geode.distributed.LocatorLauncher.run(LocatorLauncher.java:692)
    at gemfire//org.apache.geode.distributed.LocatorLauncher.main(LocatorLauncher.java:229)
    at gemfire//com.vmware.gemfire.deployment.modules.internal.Launcher.invokeMainClass(Launcher.java:99)
    at gemfire//com.vmware.gemfire.deployment.modules.internal.Launcher.launch(Launcher.java:88)
    ... 6 more
Caused by: java.net.BindException: Network is unreachable; port (10334) is not available on localhost.
    at gemfire//org.apache.geode.distributed.AbstractLauncher.assertPortAvailable(AbstractLauncher.java:152)
    at gemfire//org.apache.geode.distributed.LocatorLauncher.start(LocatorLauncher.java:780)
    ... 10 more

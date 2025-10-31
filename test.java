<settings>
  <proxies>
    <proxy>
      <id>example-proxy</id>
      <active>true</active>
      <protocol>http</protocol>
      <host>your.proxy.host</host>
      <port>8080</port>
      <username>yourUsername</username>
      <password>yourPassword</password>
      <nonProxyHosts>www.google.com|*.yourcompany.com</nonProxyHosts>
    </proxy>
  </proxies>
</settings>


  <repositories>
    <repository>
        <id>google-maven-central</id>
        <url>https://maven-central.storage-download.googleapis.com/maven2/</url>
    </repository>
</repositories>

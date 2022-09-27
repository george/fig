# Fig

Fig is a Java HTTP client designed to simplify GET/POST requests.

## Building

1) Clone the repository with `git clone https://github.com/glove/fig`
2) Execute `cd fig`
3) Build the project via `mvn package`. The compiled JAR will be found in the `fig/target` folder.

## Usage

To include Fig in your project, follow the steps below using either Maven or Gradle.

Gradle:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }

    dependencies {
        implementation 'com.github.glove:fig:main-SNAPSHOT'
    }
}
```

Maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.glove</groupId>
        <artifactId>fig</artifactId>
        <version>main-SNAPSHOT</version>
    </dependency>
</dependencies>
```

Java:

```java
public class Application {
    
    public static void main(String[] args) {
        FigWebClient webClient = new FigWebClient();

        webClient.get("https://google.com/", (response) -> {
            int responseCode = response.getResponseCode();
            String data = response.getData();

            //Response logic here
        });

        webClient.post("https://google.com", "Hello, world!", (response) -> {
            int responseCode = response.getResponseCode();
            String data = response.getData();

            //Response logic here 
        });
    }
}
```
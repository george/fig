# Fig

Fig is a Java HTTP client designed to simplify GET/POST requests.

## Usage

```
FigWebClient webClient = new FigWebClient();

webClient.getAsync("https://google.com/", (response) -> {
    int responseCode = response.getResponseCode();
    String data = response.getData();
    
    //Response logic here
});
        
webClient.postAsync("https://google.com", "Hello, world!", (response) -> {
    int responseCode = response.getResponseCode();
    String data = response.getData();

    //Additional response logic here 
});
```
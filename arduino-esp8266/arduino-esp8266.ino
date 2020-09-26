#include <ESP8266WiFi.h>

const char* ssid = "hupan-here";
const char* password = "xiongbaobao";

bool connectOk = false;
bool switchOn = false;
WiFiServer wifiServer(80);

void setup() {
    //gpio
    pinMode(13, OUTPUT);
    digitalWrite(13, 0);
    pinMode(15, OUTPUT);
    digitalWrite(15, 0);
    Serial.begin(115200);
    delay(10);

    // off
    digitalWrite(13, 1);
    delay(500);
    digitalWrite(13, 0);
    delay(10);
  
    // Connect to WiFi network
    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(ssid);

    WiFi.begin(ssid, password);
    int count = 0;
    while ((WiFi.status() != WL_CONNECTED)&&(count < 200)) {
        count++;
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.println("WiFi Connected");
    connectOk = true;
    
    // Start the server
    wifiServer.begin();
    
    // Print the IP address
    Serial.print("Server started @ ");
    Serial.println(WiFi.localIP());
}

void loop() {
    // Check if wifi is connected
    if(!connectOk){
        delay(500);
        return;
    }

    WiFiClient myClient = wifiServer.available();
    if(!myClient){
        delay(200);
        return;
    }

    String request = myClient.readStringUntil('\r');
    myClient.flush();
    praseRequest(myClient, request);       
    Serial.println(request);
    delay(100);
    
    return;
}

void praseRequest(WiFiClient wifiClient, String request){
    bool action = false;
    if(request.startsWith("POST ")){
         String url = request.substring(5);
         if(url.startsWith("/v1/switch1/on")){
             action = true;
             switchOn = true;
             wifiClient.print("HTTP/1.1 200 \r\n\r\n");
         }else if(url.startsWith("/v1/switch1/off")){
             action = true;
             switchOn = false;
             wifiClient.print("HTTP/1.1 200 \r\n\r\n");
         }else{
            wifiClient.print("HTTP/1.1 404 \r\n\r\n");
        }
    }else if(request.startsWith("GET ")){
        String url = request.substring(4);
        if(url.startsWith("/v1/switch1/status")){
             if(switchOn){
                 wifiClient.print("HTTP/1.1 200 \r\n\r\n1");
             }else{
                 wifiClient.print("HTTP/1.1 200 \r\n\r\n0");
             }
         }else{
            wifiClient.print("HTTP/1.1 404 \r\n\r\n");
        }
    }else{
        Serial.println("skip");    
    }
    
    if(action){
        if(switchOn){
            digitalWrite(15, 1);
            delay(500);
            digitalWrite(15, 0);
        } else {
            digitalWrite(13, 1);
            delay(500);
            digitalWrite(13, 0);
        }
        delay(100);
    }
    
    return;
}




